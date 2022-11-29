package com.tongfu.controller;

import com.tongfu.Util.DateUtil;
import com.tongfu.Util.FileUpload;
import com.tongfu.entity.CommitteeApply;
import com.tongfu.entity.MeetingApply;
import com.tongfu.service.CommitteeApplyService;
import com.tongfu.service.MeetingApplyService;
import com.tongfu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Controller
@RequestMapping("/committeeApply")
public class CommitteeApplyController {


    @Autowired
    private CommitteeApplyService committeeApplyService;//组委会申请

    @Autowired
    private MemberService memberService;

    //组委会申请合同
    @Value("${contractImg}")
    private String contractImgPaht;


    /**
     * 组委会申请
     * @param committeeApply
     * @param beginHoldDateText
     * @param endHoldDateText
     * @param contractImg
     * @return
     */
    @PostMapping("/save")
    public String save(CommitteeApply committeeApply, String beginHoldDateText, String endHoldDateText,
            @RequestParam("contractImg") MultipartFile contractImg
    ){

        String contract = FileUpload.upload(contractImg,contractImgPaht);
        committeeApply.setContract(contract);
        committeeApply.setBeginHoldDate(DateUtil.getStringtoDate(beginHoldDateText,"yyyy-MM-dd"));
        committeeApply.setEndHoldDate(DateUtil.getStringtoDate(endHoldDateText,"yyyy-MM-dd"));
        committeeApply.setStatus(1l);
        committeeApply.setMember(memberService.getCurrent().getId());
        committeeApply.setModifyDate(new Date());
        committeeApply.setCreateDate(new Date());
        committeeApplyService.insertSelective(committeeApply);

        return "redirect:/point/addcommittee";
    }



}
