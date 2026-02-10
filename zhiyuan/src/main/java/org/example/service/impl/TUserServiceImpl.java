package org.example.service.impl;

import org.example.entity.TUser;
import org.example.mapper.TUserMapper;
import org.example.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 普通用户表 服务实现类
 * </p>
 *
 * @author lwq
 * @since 2026-02-10
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

}
