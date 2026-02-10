<template>
  <view class="container">
    <!-- 顶部用户卡片 -->
    <view class="user-card">
      <view class="user-left">
        <view class="avatar">👤</view>
        <view class="info">
          <text class="name">{{ user.name }}</text>
          <text class="role">{{ user.role }}</text>
        </view>
      </view>

      <view class="badge">
        <text class="badge-text">在线</text>
      </view>
    </view>

    <!-- 功能菜单 -->
    <view class="section">
      <text class="section-title">账号管理</text>

      <view class="menu">
        <view class="menu-item" @click="goProfile">
          <text class="icon">📄</text>
          <text class="text">个人信息</text>
          <text class="arrow">></text>
        </view>

        <view class="menu-item" @click="goSecurity">
          <text class="icon">🔐</text>
          <text class="text">账号安全</text>
          <text class="arrow">></text>
        </view>

        <view class="menu-item" @click="goDevice">
          <text class="icon">📡</text>
          <text class="text">设备绑定</text>
          <text class="arrow">></text>
        </view>
      </view>
    </view>

    <!-- 系统设置 -->
    <view class="section">
      <text class="section-title">系统设置</text>

      <view class="menu">
        <view class="menu-item" @click="goAbout">
          <text class="icon">ℹ️</text>
          <text class="text">关于平台</text>
          <text class="arrow">></text>
        </view>

        <view class="menu-item" @click="clearCache">
          <text class="icon">🧹</text>
          <text class="text">清理缓存</text>
          <text class="arrow">></text>
        </view>

        <view class="menu-item" @click="checkUpdate">
          <text class="icon">⬆️</text>
          <text class="text">检查更新</text>
          <text class="arrow">></text>
        </view>
      </view>
    </view>

    <!-- 退出登录 -->
    <view class="logout-box">
      <button class="logout-btn" @click="logout">退出登录</button>
    </view>
	
  </view>
</template>

<script setup>
import { reactive } from "vue";

const user = reactive({
  name: "管理员",
  role: "系统管理员"
});

const goProfile = () => {
  uni.showToast({
    title: "个人信息页面待开发",
    icon: "none"
  });
};

const goSecurity = () => {
  uni.showToast({
    title: "账号安全页面待开发",
    icon: "none"
  });
};

const goDevice = () => {
  uni.showToast({
    title: "设备绑定页面待开发",
    icon: "none"
  });
};

const goAbout = () => {
  uni.showModal({
    title: "关于平台",
    content: "无人机休眠仓智慧管理平台\n版本：v1.0\n开发团队：XX组",
    showCancel: false
  });
};

const clearCache = () => {
  uni.showModal({
    title: "提示",
    content: "确认清理缓存？",
    success: (res) => {
      if (res.confirm) {
        uni.clearStorageSync();
        uni.showToast({
          title: "缓存已清理",
          icon: "success"
        });
      }
    }
  });
};

const checkUpdate = () => {
  uni.showToast({
    title: "当前已是最新版本",
    icon: "none"
  });
};

const logout = () => {
  uni.showModal({
    title: "退出登录",
    content: "确认退出当前账号？",
    success: (res) => {
      if (res.confirm) {
        uni.removeStorageSync("token");
        uni.removeStorageSync("userInfo");

        uni.showToast({
          title: "已退出登录",
          icon: "success"
        });

        setTimeout(() => {
          uni.reLaunch({
            url: "/pages/login/login"
          });
        }, 500);
      }
    }
  });
};
</script>

<style>
.container {
  padding: 20rpx 16rpx;
  background: #f8f9fb;
  min-height: 100vh;
  
}

/* 用户信息卡片 */
.user-card {
  background: linear-gradient(135deg, #1677ff, #6c5ce7);
  border-radius: 22rpx;
  padding: 34rpx 26rpx;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 8rpx 18rpx rgba(0, 0, 0, 0.08);
}

.user-left {
  display: flex;
  align-items: center;
}

.avatar {
  width: 100rpx;
  height: 100rpx;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.25);
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 48rpx;
}

.info {
  margin-left: 20rpx;
}

.name {
  font-size: 34rpx;
  font-weight: bold;
  color: white;
  display: block;
}

.role {
  margin-top: 10rpx;
  font-size: 24rpx;
  color: rgba(255, 255, 255, 0.85);
  display: block;
}

.badge {
  background: rgba(255, 255, 255, 0.25);
  padding: 10rpx 18rpx;
  border-radius: 999rpx;
}

.badge-text {
  font-size: 22rpx;
  color: white;
  font-weight: bold;
}

/* Section */
.section {
  margin-top: 26rpx;
  background: #f5f5f5;
  border-radius: 15rpx;
  padding: 10rpx 14rpx 16rpx 14rpx;
}

.section-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
  margin: 26rpx 0 18rpx 10rpx;
  display: block;
}

/* 菜单 */
.menu {
  display: flex;
  flex-direction: column;
  gap: 18rpx;
}

.menu-item {
  background: #fff;
  padding: 30rpx 26rpx;
  border-radius: 20rpx;
  display: flex;
  align-items: center;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
}

.icon {
  font-size: 40rpx;
  margin-right: 20rpx;
}

.text {
  flex: 1;
  font-size: 30rpx;
  font-weight: 500;
  color: #333;
}

.arrow {
  color: #ccc;
  font-size: 30rpx;
}

/* 退出按钮 */
.logout-box {
  margin-top: 30rpx;
}

.logout-btn {
  height: 90rpx;
  line-height: 90rpx;
  border-radius: 18rpx;
  background: #ff4d4f;
  color: white;
  font-size: 30rpx;
  font-weight: bold;
}
</style>
