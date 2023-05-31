package com.zjl.service;

import com.zjl.entity.Reply;

import java.util.List;

public interface ReplyService {
    // 获取全部管理员回复
    public List<Reply> getAllReply(String query, int pageNum, int pageSize);
    // 根据用户 id获取管理员的回复
    public Reply getReplyByUserId(String user_id, int food_id, String place_time);
    // 获取回复总数
    public int getReplyTotal();
    // 删除回复
    public void deleteReply(String admin_name, String complete_time);
}
