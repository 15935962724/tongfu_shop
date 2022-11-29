package com.tongfu.controller.weixin;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.Util.ResultUtil;
import com.tongfu.entity.Area;
import com.tongfu.entity.Member;
import com.tongfu.entity.Receiver;
import com.tongfu.service.AreaService;
import com.tongfu.service.MemberService;
import com.tongfu.service.ReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller("weixinreceiver")
@RequestMapping("/weixin/receiver")
public class ReceiverController {


	@Autowired
	private ReceiverService receiverService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private MemberService memberService;

    /**
     * 新增收货地址
     * @param params
     * @return
     */
    @PostMapping("/saveReceiver")
    @ResponseBody
    public String saveReceiver(@RequestBody JSONObject jsonObject){
        Member member = memberService.getCurrent();
        Integer count = 0;
        Receiver receiver = JSONObject.parseObject(jsonObject.toJSONString(),Receiver.class);
        if (receiver.getIsDefault()){//判断如果是默认地址，就把其他设为非默认地址
            receiverService.updateReceiverIsDefault(member.getId());
        }
        Area area = areaService.selectById(receiver.getArea());
        receiver.setAreaName(area.getFullName());
        if (receiver.getId()==null){
            receiver.setCreateDate(new Date());
            receiver.setModifyDate(new Date());
            receiver.setMember(member.getId());
            receiver.setIsDeleted(false);
            count = receiverService.insertReceiver(receiver);
        }else {
            count = receiverService.updateReceiver(receiver);
        }
        if (count>0){
            return  ResultUtil.success("操作成功");
        }
        return ResultUtil.error("操作失败");
    }

//    @PostMapping("/updateReceiver")
//    @ResponseBody
//    public String updateReceiver(@RequestBody JSONObject jsonObject ){
//        Member member = memberService.getCurrent();
//        Receiver receiver = JSONObject.parseObject(jsonObject.toJSONString(),Receiver.class);
//        Area area = areaService.selectById(receiver.getArea());
//        receiver.setAreaName(area.getFullName());
//        Integer saveReceiverCount = receiverService.updateReceiver(receiver);
//        if (saveReceiverCount>0){
//            return  ResultUtil.success("操作成功");
//        }
//        return ResultUtil.error("操作失败");
//    }

    //设为默认收货地址
    @RequestMapping("/defaultReceiver")
    @ResponseBody
    public int defReceiver(Long receiverId){
        Member member = memberService.getCurrent();
        Receiver receiver2 = receiverService.selectIsDefault(member.getId());
       if(receiver2!=null){
           receiver2.setIsDefault(false);
           receiverService.updateReceiver(receiver2);
       }
        Receiver receiver = receiverService.selectById(receiverId);
        receiver.setIsDefault(true);
        int result = receiverService.updateReceiver(receiver);
        return result;
    }

    //删除收货人
    @RequestMapping("/delReceiver")
    @ResponseBody
    public int delReceiver(Long receiverId){
        return receiverService.deleteReceiver(receiverId);
    }
    /**
     * 收货地址
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(){
        JSONObject returnJson  = new JSONObject();
        Member member = memberService.getCurrent();
        List<Map> listreceiver = receiverService.getReceiver(member.getId());
        returnJson.put("list",listreceiver);//当前用户的收货地址
        return ResultUtil.success(returnJson);
    }

}
