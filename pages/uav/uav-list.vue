<template>
  <view class="container">
    <!-- 搜索框 -->
    <view class="search-box">
      <input
        v-model="keyword"
        class="search-input"
        placeholder="搜索无人机名称 / RFID / 型号"
      />
    </view>

    <!-- 列表 -->
    <view class="uav-list">
      <view
        class="uav-card"
        v-for="item in filteredList"
        :key="item.id"
        @click="goDetail(item)"
      >
        <view class="top">
          <view class="left">
            <text class="uav-name">{{ item.name }}</text>
            <text class="uav-model">{{ item.model }}</text>
          </view>

          <view class="right">
            <text class="tag" :class="item.status">{{ item.status }}</text>
          </view>
        </view>

        <view class="mid">
          <view class="row">
            <text class="label">RFID：</text>
            <text class="value">{{ item.rfidTag }}</text>
          </view>

          <view class="row">
            <text class="label">归属者：</text>
            <text class="value">{{ item.ownerName }}</text>
          </view>

          <view class="row">
            <text class="label">区域：</text>
            <text class="value">{{ item.region }}</text>
          </view>
        </view>

        <!-- 电量条 -->
        <view class="battery">
          <text class="battery-text">电量 {{ item.battery }}%</text>
          <view class="battery-bar">
            <view class="battery-inner" :style="{ width: item.battery + '%' }"></view>
          </view>
        </view>

        <view class="bottom">
          <text class="pod" :class="{ active: item.inHibernatePod }">
            {{ item.inHibernatePod ? "在休眠仓内" : "不在休眠仓" }}
          </text>

          <text class="time">更新：{{ item.lastUpdateTime }}</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref, computed } from "vue";

const keyword = ref("");

const uavList = ref([
  {
    id: 1,
    name: "UAV-01",
    rfidTag: "RFID-0001",
    model: "DJI Mavic 3",
    ownerName: "Admin",
    status: "ONLINE",
    battery: 78,
    inHibernatePod: true,
    region: "Nanjing",
    lastUpdateTime: "2026-02-10 11:55"
  },
  {
    id: 2,
    name: "UAV-02",
    rfidTag: "RFID-0002",
    model: "DJI Air 2S",
    ownerName: "Operator",
    status: "FLYING",
    battery: 42,
    inHibernatePod: false,
    region: "Suzhou",
    lastUpdateTime: "2026-02-10 11:40"
  },
  {
    id: 3,
    name: "UAV-03",
    rfidTag: "RFID-0003",
    model: "Autel EVO II",
    ownerName: "Admin",
    status: "CHARGING",
    battery: 15,
    inHibernatePod: true,
    region: "Shanghai",
    lastUpdateTime: "2026-02-10 10:20"
  },
  {
    id: 4,
    name: "UAV-04",
    rfidTag: "RFID-0004",
    model: "DJI Mini 4 Pro",
    ownerName: "Guest",
    status: "OFFLINE",
    battery: 0,
    inHibernatePod: false,
    region: "Hangzhou",
    lastUpdateTime: "2026-02-09 21:10"
  }
]);

const filteredList = computed(() => {
  if (!keyword.value) return uavList.value;

  return uavList.value.filter((item) => {
    return (
      item.name.includes(keyword.value) ||
      item.rfidTag.includes(keyword.value) ||
      item.model.includes(keyword.value)
    );
  });
});

const goDetail = (item) => {
  uni.navigateTo({
    url: "/pages/uav/uav-detail?id=" + item.id
  });
};

</script>

<style>
.container {
  padding: 20rpx 16rpx;
  background: #f8f9fb;
  min-height: 100vh;
}

.search-box {
  margin-bottom: 20rpx;
}

.search-input {
  height: 90rpx;
  border-radius: 18rpx;
  background: white;
  padding: 0 24rpx;
  font-size: 28rpx;
  border: 1px solid #eee;
}

.uav-card {
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

.uav-name {
  font-size: 34rpx;
  font-weight: bold;
  color: #222;
}

.uav-model {
  display: block;
  margin-top: 6rpx;
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

.mid {
  margin-top: 18rpx;
}

.row {
  display: flex;
  margin-top: 10rpx;
  font-size: 26rpx;
}

.label {
  color: #666;
  width: 140rpx;
}

.value {
  color: #333;
  flex: 1;
}

.battery {
  margin-top: 18rpx;
}

.battery-text {
  font-size: 24rpx;
  color: #555;
  margin-bottom: 10rpx;
  display: block;
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

.bottom {
  margin-top: 20rpx;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.pod {
  font-size: 24rpx;
  padding: 8rpx 14rpx;
  border-radius: 999rpx;
  background: #f2f2f2;
  color: #777;
  font-weight: bold;
}

.pod.active {
  background: #e8f7ef;
  color: #2ecc71;
}

.time {
  font-size: 22rpx;
  color: #999;
}
</style>
