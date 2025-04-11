package ba.unsa.etf.membership_service.membership_service.controllers;

import ba.unsa.etf.membership_service.membership_service.services.MembershipStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nplus1-test")
public class MembershipStatisticsController {

    @Autowired
    private MembershipStatisticsService membershipStatisticsService;

    @GetMapping("/loan/{memberId}")
    public void testLoanNPlusOne(@PathVariable Long memberId) {
        membershipStatisticsService.checkNPlusOneProblemForLoans(memberId);
    }
}
