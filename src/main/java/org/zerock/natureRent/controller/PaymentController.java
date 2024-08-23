package org.zerock.natureRent.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.natureRent.dto.RentalSaveDTO;
import org.zerock.natureRent.entity.Member;
import org.zerock.natureRent.security.dto.MemberDTO;
import org.zerock.natureRent.service.PaymentService;
import org.zerock.natureRent.service.RefundService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final RefundService refundService;
    private IamportClient iamportClient;

    @Value("${imp.api.key}")
    private String apiKey;

    @Value("${imp.api.secretkey}")
    private String secretKey;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, secretKey);
    }

    @PostMapping("/order/payment")
    public ResponseEntity<String> paymentComplete(@AuthenticationPrincipal MemberDTO authMember/*@Login SessionUser sessionUser*/, @RequestBody List<Map<String, Object>> orderSaveDtos) throws IOException {
        if (orderSaveDtos == null || orderSaveDtos.isEmpty()) {
            return new ResponseEntity<>("주문 정보가 없습니다.", HttpStatus.BAD_REQUEST);
        }
        log.info("orderSaveDtos size: {}", orderSaveDtos.size());



        List<RentalSaveDTO> rentals = new ArrayList<>();

        for (Map<String, Object> orderMap : orderSaveDtos) {
            RentalSaveDTO dto = new RentalSaveDTO();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

            // Map에서 productId 값을 가져와 mno로 설정
            dto.setMno(Long.valueOf(orderMap.get("productId").toString()));
            dto.setOrderPrice((Integer) orderMap.get("orderPrice"));
            dto.setQuantity((Integer) orderMap.get("orderCount"));
            dto.setReceiverName(orderMap.get("receiverName").toString());
            dto.setPhoneNumber(orderMap.get("phoneNumber").toString());
            dto.setOrderNumber(orderMap.get("orderNumber").toString());
            dto.setZipcode((Integer) orderMap.get("zipcode"));
            dto.setAddress(orderMap.get("address").toString());
            dto.setOrderRequired(orderMap.get("orderRequired").toString());
            dto.setPaymentMethod(orderMap.get("paymentMethod").toString());
            dto.setRentalStartDate(LocalDateTime.parse(orderMap.get("rentalStartDate").toString(), formatter));
            dto.setRentalEndDate(LocalDateTime.parse(orderMap.get("rentalEndDate").toString(), formatter));            rentals.add(dto);
        }










//        for (Map<String, Object> orderMap : orderSaveDtos) {
//            if (orderMap.getMno() == null) {
//                log.error("Product ID must not be null or empty");
//                return new ResponseEntity<>("Product ID가 비어 있습니다.", HttpStatus.BAD_REQUEST);
//            }
//        }

        String orderNumber = String.valueOf(rentals.get(0).getOrderNumber());
        try {
            Member member = authMember.getMember();
//            Long userId = sessionUser.getUserIdNo();
            paymentService.saveRental(member.getEmail(), rentals);
            log.info("결제 성공 : 주문 번호 {}", orderNumber);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.info("주문 상품 환불 진행 : 주문 번호 {}", orderNumber);
            String token = refundService.getToken(apiKey, secretKey);
            refundService.refundRequest(token, orderNumber, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


//    여기서 사용된 validateIamport 메서드의 paymentByImpUid()는  imp_uid(결제 고유 ID) 값을 받아 결제 상세 내역을 조회하는 함수이다.
//    이 imp_uid 값은 발급받은 "가맹정 식별 코드"이고 클라이언트 측에서 요청할 때 넣어줄 것이다.
//    이 imp_uid 값으로 iamport에 검증 요청을 보내고, 해당 거래의 상세 정보를 조회하고 반환한다.
//    출처: https://hstory0208.tistory.com/entry/Spring-아임포트import로-결제-기능-구현하기-클라이언트-서버-코드-포함 [< Hyun / Log >:티스토리]
    @PostMapping("/payment/validation/{imp_uid}")
    @ResponseBody
    public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid) {
        IamportResponse<Payment> payment = iamportClient.paymentByImpUid(imp_uid);
        log.info("결제 요청 응답. 결제 내역 - 주문 번호: {}", payment.getResponse().getMerchantUid());
        return payment;
    }







}
