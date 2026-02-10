<template>
  <view class="container">
    <!-- 顶部标题 -->
    <view class="header">
      <view>
        <text class="title">语音控制</text>
        <text class="sub">支持语音输入与地图定位</text>
      </view>

      <view class="status">
        <view class="dot"></view>
        <text class="status-text">Ready</text>
      </view>
    </view>

    <!-- 地图区域 -->
    <view class="card">
      <text class="section-title">地图定位</text>

      <view class="map-box">
        <text class="map-tip">地图模块占位（后续接入 Map API）</text>
      </view>

      <view class="map-info">
        <text class="info">当前坐标：{{ location }}</text>
        <text class="info">目标无人机：{{ currentUav }}</text>
      </view>
    </view>

    <!-- 语音输入 -->
    <view class="card">
      <text class="section-title">语音输入</text>

      <view class="voice-box">
        <view class="voice-btn" :class="{ active: isRecording }" @click="toggleRecord">
          <text class="mic">{{ isRecording ? "🎙" : "🎤" }}</text>
          <text class="voice-text">{{ isRecording ? "正在录音..." : "点击开始语音输入" }}</text>
        </view>

        <view class="result-box">
          <text class="result-title">识别结果</text>
          <text class="result-text">{{ voiceResult }}</text>
        </view>
      </view>
    </view>

    <!-- 快捷指令 -->
    <view class="card">
      <text class="section-title">快捷指令</text>

      <view class="cmd-grid">
        <view class="cmd-item blue" @click="sendCommand('TAKE_OFF')">
          <text class="cmd-name">起飞</text>
          <text class="cmd-sub">TAKE_OFF</text>
        </view>

        <view class="cmd-item orange" @click="sendCommand('LAND')">
          <text class="cmd-name">降落</text>
          <text class="cmd-sub">LAND</text>
        </view>

        <view class="cmd-item green" @click="sendCommand('RETURN_HOME')">
          <text class="cmd-name">返航</text>
          <text class="cmd-sub">RETURN_HOME</text>
        </view>

        <view class="cmd-item red" @click="sendCommand('STOP')">
          <text class="cmd-name">停止任务</text>
          <text class="cmd-sub">STOP</text>
        </view>
      </view>
    </view>

    <!-- 控制日志 -->
    <view class="card">
      <text class="section-title">控制日志</text>

      <view v-if="logList.length === 0" class="empty">
        <text>暂无日志</text>
      </view>

      <view v-for="(item, index) in logList" :key="index" class="log-item">
        <view class="log-left">
          <text class="log-title">{{ item.text }}</text>
          <text class="log-time">{{ item.time }}</text>
        </view>
        <text class="log-tag">{{ item.type }}</text>
      </view>
    </view>

    <view style="height: 40rpx;"></view>
  </view>
</template>

<script setup>
import { ref } from "vue";

const isRecording = ref(false);

const location = ref("32.06, 118.78");
const currentUav = ref("UAV-01");

const voiceResult = ref("（等待语音输入）");

const logList = ref([
  { text: "系统初始化完成", time: "10:00", type: "INFO" },
  { text: "连接无人机 UAV-01", time: "10:01", type: "INFO" }
]);

const toggleRecord = () => {
  isRecording.value = !isRecording.value;

  if (isRecording.value) {
    voiceResult.value = "（录音中...）";

    // 模拟语音识别结果
    setTimeout(() => {
      voiceResult.value = "无人机一号起飞";
      addLog("语音识别: 无人机一号起飞", "VOICE");
      isRecording.value = false;
    }, 2000);
  } else {
    voiceResult.value = "（录音已停止）";
  }
};

const sendCommand = (cmd) => {
  uni.showToast({
    title: "已发送: " + cmd,
    icon: "none"
  });

  addLog("下发指令: " + cmd, "CMD");

  // TODO 后端预留接口
  // POST /api/uav/command
};

const addLog = (text, type) => {
  const now = new Date();
  const time = now.getHours().toString().padStart(2, "0") + ":" +
               now.getMinutes().toString().padStart(2, "0");

  logList.value.unshift({
    text,
    time,
    type
  });
};
</script>

<style>
.container {
  padding: 20rpx 16rpx;
  background: #f8f9fb;
  min-height: 100vh;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20rpx;
  margin-left: 15rpx;
}

.title {
  font-size: 44rpx;
  font-weight: bold;
  color: #222;
}

.sub {
  display: block;
  margin-top: 6rpx;
  font-size: 24rpx;
  color: #888;
}

.status {
  display: flex;
  align-items: center;
  background: #eefaf3;
  padding: 10rpx 18rpx;
  border-radius: 40rpx;
}

.dot {
  width: 12rpx;
  height: 12rpx;
  background: #27ae60;
  border-radius: 50%;
  margin-right: 10rpx;
}

.status-text {
  font-size: 24rpx;
  color: #27ae60;
  font-weight: bold;
}

.card {
  background: white;
  border-radius: 20rpx;
  padding: 26rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 6rpx 16rpx rgba(0, 0, 0, 0.05);
}

.section-title {
  font-size: 30rpx;
  font-weight: bold;
  margin-bottom: 18rpx;
  color: #333;
  display: block;
}

.map-box {
  height: 260rpx;
  background: #f2f2f2;
  border-radius: 18rpx;
  display: flex;
  justify-content: center;
  align-items: center;
}

.map-tip {
  font-size: 26rpx;
  color: #999;
}

.map-info {
  margin-top: 16rpx;
}

.info {
  display: block;
  font-size: 26rpx;
  color: #555;
  margin-top: 6rpx;
}

.voice-box {
  display: flex;
  flex-direction: column;
  gap: 20rpx;
}

.voice-btn {
  height: 140rpx;
  border-radius: 20rpx;
  background: #f5f6fa;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.voice-btn.active {
  background: #e8f0ff;
}

.mic {
  font-size: 52rpx;
}

.voice-text {
  margin-top: 12rpx;
  font-size: 26rpx;
  color: #333;
  font-weight: 500;
}

.result-box {
  background: #f8f9fb;
  border-radius: 18rpx;
  padding: 20rpx;
}

.result-title {
  font-size: 24rpx;
  font-weight: bold;
  color: #555;
  display: block;
  margin-bottom: 10rpx;
}

.result-text {
  font-size: 28rpx;
  color: #222;
}

.cmd-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 18rpx;
}

.cmd-item {
  padding: 26rpx;
  border-radius: 18rpx;
  color: white;
}

.cmd-name {
  font-size: 30rpx;
  font-weight: bold;
  display: block;
}

.cmd-sub {
  font-size: 22rpx;
  opacity: 0.9;
  margin-top: 8rpx;
  display: block;
}

.blue {
  background: #1677ff;
}

.orange {
  background: #f39c12;
}

.green {
  background: #2ecc71;
}

.red {
  background: #ff4d4f;
}

.log-item {
  background: #f8f9fb;
  padding: 20rpx;
  border-radius: 16rpx;
  margin-bottom: 14rpx;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.log-title {
  font-size: 26rpx;
  font-weight: 500;
  color: #222;
  display: block;
}

.log-time {
  font-size: 22rpx;
  color: #888;
  margin-top: 6rpx;
  display: block;
}

.log-tag {
  font-size: 22rpx;
  font-weight: bold;
  color: #555;
}

.empty {
  padding: 30rpx;
  text-align: center;
  color: #999;
}
</style>
