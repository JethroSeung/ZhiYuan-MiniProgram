package org.example.service.impl;

import org.example.entity.TUserAddress;
import org.example.mapper.TUserAddressMapper;
import org.example.service.ITUserAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户地址表 服务实现类
 * </p>
 *
 * @author lwq
 * @since 2026-02-10
 */
@Service
public class TUserAddressServiceImpl extends ServiceImpl<TUserAddressMapper, TUserAddress> implements ITUserAddressService {

}
