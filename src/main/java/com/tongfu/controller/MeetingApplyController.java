package com.tongfu.controller;

import com.tongfu.Util.DateUtil;
import com.tongfu.Util.FileUpload;
import com.tongfu.entity.MeetingApply;
import com.tongfu.entity.Member;
import com.tongfu.service.MeetingApplyService;
import com.tongfu.service.MemberPointLogService;
import com.tongfu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/meetingApply")
public class MeetingApplyController {

    @Autowired
    private MeetingApplyService meetingApplyService;//会议申请

    @Autowired
    private MemberService memberService;

    //医生会议申请邀请函
    @Value("${invitationImg}")
    private String invitationImgPaht;


    /**
     * 医生会议申请
     * @param meetingApply
     * @return
     */
    @PostMapping("/save")
    public String save( MeetingApply meetingApply ,String beginHoldDateText,
                        String gotimeText,String backtimeText
            , @RequestParam("invitationImg") MultipartFile invitationImg
    ){

        String invitation = FileUpload.upload(invitationImg,invitationImgPaht);
        meetingApply.setInvitation(invitation);
        meetingApply.setBeginHoldDate(DateUtil.getStringtoDate(beginHoldDateText,"yyyy-MM-dd"));
        meetingApply.setGotime(DateUtil.getStringtoDate(gotimeText,"yyyy-MM-dd"));
        if (meetingApply.getTraffic()==2){
            meetingApply.setBacktime(DateUtil.getStringtoDate(backtimeText,"yyyy-MM-dd"));
        }
        meetingApply.setStatus(1l);
        meetingApply.setMember(memberService.getCurrent().getId());
        meetingApply.setModifyDate(new Date());
        meetingApply.setCreateDate(new Date());
        meetingApplyService.insertSelective(meetingApply);


        return "/point/doctorApply";
    }



}
