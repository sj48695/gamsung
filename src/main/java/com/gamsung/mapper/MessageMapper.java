package com.gamsung.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gamsung.vo.InChatMessageVO;

@Mapper
public interface MessageMapper {

	List<InChatMessageVO> selectMessageList(HashMap<String, String> params);

}
