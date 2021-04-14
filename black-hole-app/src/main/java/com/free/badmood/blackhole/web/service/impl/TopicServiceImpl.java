package com.free.badmood.blackhole.web.service.impl;

import com.free.badmood.blackhole.web.entity.Topic;
import com.free.badmood.blackhole.web.mapper.TopicMapper;
import com.free.badmood.blackhole.web.service.ITopicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 话题表 服务实现类
 * </p>
 *
 * @author wadexi
 * @since 2021-04-14
 */
@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements ITopicService {

}
