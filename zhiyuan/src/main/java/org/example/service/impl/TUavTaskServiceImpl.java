package org.example.service.impl;

import org.example.entity.TUavTask;
import org.example.mapper.TUavTaskMapper;
import org.example.service.ITUavTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 无人机配送任务表 服务实现类
 * </p>
 *
 * @author lwq
 * @since 2026-02-10
 */
@Service
public class TUavTaskServiceImpl extends ServiceImpl<TUavTaskMapper, TUavTask> implements ITUavTaskService {

}
