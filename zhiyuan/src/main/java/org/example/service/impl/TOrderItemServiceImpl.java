package org.example.service.impl;

import org.example.entity.TOrderItem;
import org.example.mapper.TOrderItemMapper;
import org.example.service.ITOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author lwq
 * @since 2026-02-10
 */
@Service
public class TOrderItemServiceImpl extends ServiceImpl<TOrderItemMapper, TOrderItem> implements ITOrderItemService {

}
