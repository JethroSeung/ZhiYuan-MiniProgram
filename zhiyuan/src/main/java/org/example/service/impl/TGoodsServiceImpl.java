package org.example.service.impl;

import org.example.entity.TGoods;
import org.example.mapper.TGoodsMapper;
import org.example.service.ITGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author lwq
 * @since 2026-02-10
 */
@Service
public class TGoodsServiceImpl extends ServiceImpl<TGoodsMapper, TGoods> implements ITGoodsService {

}
