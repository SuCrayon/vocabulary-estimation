package com.crayon.ve.service.impl;

import com.crayon.ve.entity.Word;
import com.crayon.ve.mapper.WordMapper;
import com.crayon.ve.service.WordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 单词表 服务实现类
 * </p>
 *
 * @author Crayon
 * @since 2021-06-23
 */
@Service
public class WordServiceImpl extends ServiceImpl<WordMapper, Word> implements WordService {

}
