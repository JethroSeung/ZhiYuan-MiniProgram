<template>
  <view class="container">
    <!-- 基础信息卡片 -->
    <view class="card">
      <view class="top">
        <view>
          <text class="name">{{ uav.name }}</text>
          <text class="model">{{ uav.model }}</text>
        </view>

        <text class="tag" :class="uav.status">{{ uav.status }}</text>
      </view>

      <view class="info-list">
        <view class="row">
          <text class="label">RFID</text>
          <text class="value">{{ uav.rfidTag }}</text>
        </view>

        <view class="row">
          <text class="label">归属者</text>
          <text class="value">{{ uav.ownerName }}</text>
        </view>

        <view class="row">
          <text class="label">区域</text>
          <text class="value">{{ uav.region }}</text>
        </view>

        <view class="row">
          <text class="label">是否在休眠仓</text>
          <text class="value">{{ uav.inHibernatePod ? "是" : "否" }}</text>
        </view>
      </view>
    </view>

    <!-- 电量信息 -->
    <view class="card">
      <text class="section-title">电量状态</text>

      <view class="battery-box">
        <text class="battery-num">{{ uav.battery }}%</text>
        <view class="battery-bar">
          <view class="battery-inner" :style="{ width: uav.battery + '%' }"></view>
        </view>
      </view>
    </view>

    <!-- 飞行数据 -->
    <view class="card">
      <text class="section-title">飞行数据</text>

      <view class="info-list">
        <view class="row">
          <text class="label">高度</text>
          <text class="value">{{ uav.altitude }} m</text>
        </view>

        <view class="row">
          <text class="label">速度</text>
          <text class="value">{{ uav.speed }} m/s</text>
        </view>

        <view class="row">
          <text class="label">GPS</text>
          <text class="value">{{ uav.gps }}</text>
        </view>

        <view class="row">
          <text class="label">最后更新时间</text>
          <text class="value">{{ uav.lastUpdateTime }}</text>
        </view>
      </view>
    </view>

    <!-- 控制按钮 -->
    <view class="card">
      <text class="section-title">快捷控制</text>

      <view class="btn-grid">
        <button class="btn primary" @click="sendCommand('TAKE_OFF')">起飞</button>
        <button class="btn warn" @click="sendCommand('LAND')">降落</button>
        <button class="btn danger" @click="sendCommand('RETURN_HOME')">返航</button>
        <button class="btn gray" @click="sendCommand('STOP')">停止任务</button>
      </view>
    </view>

    <!-- 跳转语音控制 -->
    <view class="card">
      <text class="section-title">语音控制</text>
      <button class="btn voice" @click="goVoice">进入语音控制模块</button>
    </view>

    <view style="height: 40rpx;"></view>
  </view>
</template>

<script setup>
import { ref, onMounted } from "vue";

const uav = ref({
  id: null,
  name: "UAV-01",
  rfidTag: "RFID-0001",
  model: "DJI Mavic 3",
  ownerName: "Admin",
  status: "ONLINE",
  battery: 78,
  inHibernatePod: true,
  region: "Nanjing",
  altitude: 30,
  speed: 5.2,
  gps: "32.06, 118.78",
  lastUpdateTime: "2026-02-10 12:10"
});

onMounted(() => {
  // 获取页面栈
  const pages = getCurrentPages();
  const currentPage = pages[pages.length - 1];

  // uni-app 页面参数一般在 options 里
  const options = currentPage.options || {};

  console.log("uav detail options:", options);

  if (options.id) {
    uav.value.id = options.id;
  }
});

const sendCommand = (cmd) => {
  uni.showModal({
    title: "确认操作",
    content: "是否执行指令：" + cmd + " ?",
    success: (res) => {
      if (res.confirm) {
        uni.showToast({
          title: "已发送指令: " + cmd,
          icon: "none"
        });
      }
    }
  });
};

const goVoice = () => {
  uni.switchTab({
    url: "/pages/voice/voice"
  });
};
</script>


<style>
.container {
  padding: 20rpx 16rpx;
  background: #f8f9fb;
  min-height: 100vh;
}

.card {
  background: white;
  border-radius: 20rpx;
  padding: 26rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 6rpx 16rpx rgba(0, 0, 0, 0.05);
}

.top {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.name {
  font-size: 36rpx;
  font-weight: bold;
  color: #222;
}

.model {
  display: block;
  margin-top: 8rpx;
  font-size: 24rpx;
  color: #888;
}

.tag {
  font-size: 22rpx;
  padding: 8rpx 14rpx;
  border-radius: 10rpx;
  font-weight: bold;
  color: white;
}

.ONLINE {
  background: #2ecc71;
}

.OFFLINE {
  background: #7f8c8d;
}

.FLYING {
  background: #1677ff;
}

.CHARGING {
  background: #f39c12;
}

.section-title {
  font-size: 30rpx;
  font-weight: bold;
  margin-bottom: 18rpx;
  color: #333;
  display: block;
}

.info-list {
  margin-top: 10rpx;
}

.row {
  display: flex;
  justify-content: space-between;
  margin-top: 16rpx;
}

.label {
  font-size: 26rpx;
  color: #666;
}

.value {
  font-size: 26rpx;
  color: #333;
  font-weight: 500;
}

.battery-box {
  margin-top: 10rpx;
}

.battery-num {
  font-size: 40rpx;
  font-weight: bold;
  color: #2ecc71;
  display: block;
  margin-bottom: 12rpx;
}

.battery-bar {
  height: 18rpx;
  border-radius: 999rpx;
  background: #eee;
  overflow: hidden;
}

.battery-inner {
  height: 100%;
  background: #2ecc71;
  border-radius: 999rpx;
}

.btn-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 18rpx;
  margin-top: 10rpx;
}

.btn {
  height: 90rpx;
  line-height: 90rpx;
  border-radius: 16rpx;
  font-size: 28rpx;
  font-weight: bold;
  color: white;
}

.primary {
  background: #1677ff;
}

.warn {
  background: #f39c12;
}

.danger {
  background: #ff4d4f;
}

.gray {
  background: #7f8c8d;
}

.voice {
  background: #6c5ce7;
}
</style>
