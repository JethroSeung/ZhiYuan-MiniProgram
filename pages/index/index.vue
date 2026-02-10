<template>
  <view class="container">
    <!-- 顶部状态 -->
    <view class="header">
      <view class="welcome">
        <text class="title">Dashboard</text>
        <text class="sub">无人机管理概览</text>
      </view>
      <view class="status">
        <view class="dot"></view>
        <text class="status-text">Connected</text>
      </view>
    </view>

    <!-- 运行统计：改为单列大卡片 -->
    <view class="section">
      <text class="section-title">运行统计</text>
      <view class="stat-list">
        <view class="stat-card">
          <text class="stat-label">无人机总数</text>
          <text class="stat-num blue">{{ stats.totalUav }}</text>
        </view>

        <view class="stat-card">
          <text class="stat-label">在线无人机</text>
          <text class="stat-num green">{{ stats.onlineUav }}</text>
        </view>

        <view class="stat-card">
          <text class="stat-label">休眠仓内</text>
          <text class="stat-num gray">{{ stats.inPod }}</text>
        </view>

        <view class="stat-card">
          <text class="stat-label">告警数量</text>
          <text class="stat-num red">{{ stats.alerts }}</text>
        </view>
      </view>
    </view>

  
    <view class="section">
      <text class="section-title">快捷功能</text>
      <view class="quick-list">
        <view class="quick-item" @click="goUavList">
          <text class="quick-icon">🚁</text>
          <text class="quick-text">无人机列表</text>
          <text class="arrow">></text>
        </view>

        <view class="quick-item">
          <text class="quick-icon">🗺</text>
          <text class="quick-text">地图查看</text>
          <text class="arrow">></text>
        </view>

        <view class="quick-item">
          <text class="quick-icon">📋</text>
          <text class="quick-text">飞行日志</text>
          <text class="arrow">></text>
        </view>
      </view>
    </view>

    <!-- 最近告警 -->
    <view class="section last_section">
      <text class="section-title">最近告警</text>
      <view class="alert-item" v-for="(item, index) in alertList" :key="index">
        <view class="alert-left">
          <text class="alert-title">{{ item.title }}</text>
          <text class="alert-sub">{{ item.time }}</text>
        </view>
        <text class="alert-tag" :class="item.level">{{ item.level }}</text>
      </view>
    </view>
    
    <!-- 底部安全距离 -->
    <view style="height: 6rpx;"></view>
  </view>
</template>

<script setup>
import { reactive, ref } from "vue";

const stats = reactive({
  totalUav: 6,
  onlineUav: 4,
  inPod: 2,
  alerts: 3
});

const alertList = ref([
  { title: "UAV-02 电量低于 20%", time: "10:21", level: "HIGH" },
  { title: "UAV-05 信号弱", time: "09:47", level: "MID" },
  { title: "休眠仓 3 号舱门异常", time: "昨天 18:30", level: "LOW" }
]);

const goUavList = () => {
  uni.switchTab({
    url: "/pages/uav/uav-list"
  });
};
</script>

<style>
.section{
	background-color: #F5F5F5;
	margin-bottom:20rpx ;
	border-radius: 15rpx;
	width: 100%;
	padding: 5rpx 14rpx 12rpx 14rpx;
}

.last_section{
	margin-bottom: 0;
}

/* 核心容器 */
.container {
  padding: 20rpx 15rpx 0 15rpx;
  background-color: #f8f9fb;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

/* 顶部栏 */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30rpx;
}

.welcome .title {
  font-size: 46rpx;
  font-weight: bold;
  color: #1a1a1a;
  margin-left: 6rpx;
}

.welcome .sub {
  font-size: 24rpx;
  color: #999;
  margin-top: 4rpx;
  margin-left: 6rpx;

}

.status {
  display: flex;
  align-items: center;
  background: #eefaf3;
  padding: 10rpx 20rpx;
  border-radius: 40rpx;
  margin-right: -8rpx;
}

.dot {
  width: 12rpx;
  height: 12rpx;
  background: #27ae60;
  border-radius: 50%;
  margin-right: 12rpx;
}

.status-text {
  font-size: 24rpx;
  color: #27ae60;
  font-weight: bold;
}

/* 公用标题 */
.section-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
  margin: 30rpx 0 20rpx 10rpx;
  display: block;
}

/* 统计卡片 - 竖向单列列表 */
.stat-list {
  display: flex;
  flex-direction: column;
  gap: 20rpx;
}

.stat-card {
  background: #fff;
  padding: 30rpx 40rpx;
  border-radius: 20rpx;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 4rpx 12rpx rgba(0,0,0,0.05);
}

.stat-label {
  font-size: 30rpx;
  color: #666;
}

.stat-num {
  font-size: 40rpx;
  font-weight: bold;
}

.blue { color: #007aff; }
.green { color: #2ecc71; }
.gray { color: #7f8c8d; }
.red { color: #e74c3c; }

/* 快捷功能 - 横向长条 */
.quick-list {
  display: flex;
  flex-direction: column;
  gap: 20rpx;
}

.quick-item {
  background: #fff;
  padding: 34rpx 40rpx;
  border-radius: 20rpx;
  display: flex;
  align-items: center;
  box-shadow: 0 4rpx 12rpx rgba(0,0,0,0.05);
}

.quick-icon {
  font-size: 44rpx;
  margin-right: 30rpx;
}

.quick-text {
  flex: 1;
  font-size: 30rpx;
  font-weight: 500;
  color: #333;
}

.arrow {
  color: #ccc;
  font-size: 30rpx;
}

/* 告警列表 */
.alert-item {
  background: #fff;
  padding: 26rpx 30rpx;
  border-radius: 20rpx;
  margin-bottom: 20rpx;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 4rpx 12rpx rgba(0,0,0,0.05);
}

.alert-title {
  font-size: 28rpx;
  font-weight: 500;
  color: #333;
}

.alert-sub {
  font-size: 22rpx;
  color: #999;
  margin-top: 6rpx;
  display: block;
}

.alert-tag {
  padding: 6rpx 16rpx;
  border-radius: 8rpx;
  font-size: 20rpx;
  color: #fff;
  font-weight: bold;
}

.HIGH { background: #e74c3c; }
.MID { background: #f39c12; }
.LOW { background: #27ae60; }
</style>