package com.zjl.service.impls;

import com.zjl.dao.PicDao;
import com.zjl.entity.HomePic;
import com.zjl.entity.ListPic;
import com.zjl.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PicServiceImpl implements PicService {
    @Autowired
    PicDao picDao;

    @Override
    public List<HomePic> getAllHomePic() {
        return picDao.getAllHomePic();
    }

    @Override
    public void addHomePic(int id, String homePic) {
        picDao.addHomePic(id, homePic);
    }

    @Override
    public void updateHomePic(int id, String homePic) {
        picDao.updateHomePic(id, homePic);
    }

    @Override
    public Boolean selectHomePicId(int id) {
        return picDao.selectHomePicId(id);
    }

    @Override
    public void deleteHomePic() {
        picDao.deleteHomePic();
    }

    @Override
    public List<ListPic> getAllListPic() {
        return picDao.getAllListPic();
    }

    @Override
    public void addListPic(int id, String listPic) {
        picDao.addListPic(id, listPic);
    }

    @Override
    public void updateListPic(int id, String listPic) {
        picDao.updateListPic(id, listPic);
    }

    @Override
    public Boolean selectListPicId(int id) {
        return picDao.selectListPicId(id);
    }

    @Override
    public void deleteListPic() {
        picDao.deleteListPic();
    }
}
