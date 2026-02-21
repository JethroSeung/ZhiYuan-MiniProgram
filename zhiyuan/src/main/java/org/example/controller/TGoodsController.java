package org.example.controller;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.entity.TGoods;
import org.example.service.ITGoodsService;
import org.example.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author lwq
 * @since 2026-02-10
 */
@RestController
@RequestMapping("/goods")
public class TGoodsController {
    @Autowired
    private ITGoodsService goodsService;

    /**
     * 分页查询商品列表
     * @param page 当前页
     * @param limit 每页显示条数
     * @param name 商品名称（模糊查询）
     * @param category 分类
     * @param status 状态
     * @return
     */
    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       String name, String category, Integer status) {
        Page<TGoods> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<TGoods> wrapper = new LambdaQueryWrapper<>();

        // 添加查询条件
        wrapper.like(name != null && !name.isEmpty(), TGoods::getName, name)
                .eq(category != null && !category.isEmpty(), TGoods::getCategory, category)
                .eq(status != null, TGoods::getStatus, status)
                .orderByDesc(TGoods::getCreateTime);

        IPage<TGoods> pageResult = goodsService.page(pageParam, wrapper);
        return Result.success(pageResult);
    }

    /**
     * 获取所有商品列表（不分页）
     * @return
     */
    @GetMapping("/list")
    public Result list() {
        List<TGoods> list = goodsService.list();
        return Result.success(list);
    }

    /**
     * 根据ID查询商品详情
     * @param id 商品ID
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        TGoods goods = goodsService.getById(id);
        if (goods != null) {
            return Result.success(goods);
        }
        return Result.error("商品不存在");
    }

    /**
     * 新增商品
     * @param goods 商品信息
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody TGoods goods) {
        boolean save = goodsService.save(goods);
        if (save) {
            return Result.success("新增成功");
        }
        return Result.error("新增失败");
    }

    /**
     * 修改商品
     * @param goods 商品信息
     * @return
     */
    @PutMapping
    public Result update(@RequestBody TGoods goods) {
        boolean update = goodsService.updateById(goods);
        if (update) {
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }

    /**
     * 删除商品
     * @param id 商品ID
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean remove = goodsService.removeById(id);
        if (remove) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 批量删除商品
     * @param ids 商品ID数组
     * @return
     */
    @DeleteMapping("/batch")
    public Result batchDelete(@RequestBody Long[] ids) {
        boolean remove = goodsService.removeByIds(Arrays.asList(ids));
        if (remove) {
            return Result.success("批量删除成功");
        }
        return Result.error("批量删除失败");
    }

    /**
     * 更新商品状态
     * @param id 商品ID
     * @param status 状态(1上架 0下架)
     * @return
     */
    @PutMapping("/status/{id}")
    public Result updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        TGoods goods = new TGoods();
        goods.setId(id);
        goods.setStatus(status);
        boolean update = goodsService.updateById(goods);
        if (update) {
            return Result.success("状态更新成功");
        }
        return Result.error("状态更新失败");
    }

    /**
     * 根据分类统计商品数量
     * @return
     */
    @GetMapping("/statistics/category")
    public Result statisticsByCategory() {
        List<TGoods> list = goodsService.list();
        // 简单统计示例，实际可以使用SQL分组查询
        long foodCount = list.stream().filter(g -> "food".equals(g.getCategory())).count();
        long medicineCount = list.stream().filter(g -> "medicine".equals(g.getCategory())).count();
        long lifeCount = list.stream().filter(g -> "life".equals(g.getCategory())).count();
        long industryCount = list.stream().filter(g -> "industry".equals(g.getCategory())).count();

        return Result.success(new CategoryStatistics(foodCount, medicineCount, lifeCount, industryCount));
    }

    /**
     * 内部类：分类统计结果
     */
    @Data
    static class CategoryStatistics {
        // getters and setters
        private long food;
        private long medicine;
        private long life;
        private long industry;

        public CategoryStatistics(long food, long medicine, long life, long industry) {
            this.food = food;
            this.medicine = medicine;
            this.life = life;
            this.industry = industry;
        }

    }
}
