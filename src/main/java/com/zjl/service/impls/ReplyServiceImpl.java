package com.zjl.service.impls;

import com.zjl.dao.ReplyDao;
import com.zjl.entity.Reply;
import com.zjl.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    ReplyDao replyDao;

    @Override
    public List<Reply> getAllReply(String query, int pageNum, int pageSize) {
        return replyDao.getAllReply(query, pageNum, pageSize);
    }

    @Override
    public Reply getReplyByUserId(String user_id, int food_id, String place_time) {
        return replyDao.getReplyByUserId(user_id, food_id, place_time);
    }

    @Override
    public int getReplyTotal() {
        return replyDao.getReplyTotal();
    }

    @Override
    public void deleteReply(String admin_name, String complete_time) {
        replyDao.deleteReply(admin_name, complete_time);
    }
}
