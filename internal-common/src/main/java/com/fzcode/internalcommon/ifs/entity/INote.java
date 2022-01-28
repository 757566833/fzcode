package com.fzcode.internalcommon.ifs.entity;

import java.util.Date;
import java.util.List;

public interface INote {
      Integer id = null;
      String title = null;
      String content= null;
      List<Integer> categories= null;
      Date createTime= null;
      Date updateTime= null;
      Integer createBy= null;
      String summary= null;
}
