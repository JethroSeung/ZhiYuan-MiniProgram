<template>
  <view class="container">
    <view class="card">
      <view class="title">无人机休眠仓智慧管理平台</view>
      <view class="sub">请登录后继续使用</view>

      <view class="form">
        <view class="input-item">
          <text class="label">账号</text>
          <input v-model="username" class="input" placeholder="请输入账号" />
        </view>

        <view class="input-item">
          <text class="label">密码</text>
          <input v-model="password" class="input" password placeholder="请输入密码" />
        </view>

        <button class="btn" @click="handleLogin">登录</button>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref } from "vue";

const username = ref("");
const password = ref("");

const handleLogin = async () => {
  if (!username.value || !password.value) {
    uni.showToast({
      title: "请输入账号和密码",
      icon: "none"
    });
    return;
  }

  uni.showLoading({ title: "登录中..." });

  try {
    // TODO：后端接口地址后面我们会统一封装到 request.js
    const res = await uni.request({
      url: "http://localhost:8080/api/login",
      method: "POST",
      data: {
        username: username.value,
        password: password.value
      }
    });

    uni.hideLoading();

    // uni.request 返回结构：res.data 才是后端数据
    const data = res.data;

    if (data && data.code === 200) {
      // 假设后端返回 token 字段
      uni.setStorageSync("token", data.token);
      uni.setStorageSync("userInfo", data.user);

      uni.showToast({
        title: "登录成功",
        icon: "success"
      });

      setTimeout(() => {
        uni.switchTab({
          url: "/pages/index/index"
        });
      }, 600);
    } else {
      uni.showToast({
        title: data?.msg || "登录失败",
        icon: "none"
      });
    }
  } catch (err) {
    uni.hideLoading();
    uni.showToast({
      title: "网络错误",
      icon: "none"
    });
    console.error("login error:", err);
  }
};
</script>

<style>
.container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #f5f7fa;
  padding: 40rpx;
}

.card {
  width: 100%;
  max-width: 650rpx;
  background: #fff;
  border-radius: 30rpx;
  padding: 50rpx 40rpx;
  box-shadow: 0 10rpx 30rpx rgba(0, 0, 0, 0.08);
  transform: translateY(-140rpx);
}

.title {
  font-size: 40rpx;
  font-weight: 700;
  color: #222;
  text-align: center;
}

.sub {
  margin-top: 15rpx;
  font-size: 26rpx;
  color: #777;
  text-align: center;
}

.form {
  margin-top: 50rpx;
}

.input-item {
  margin-bottom: 30rpx;
}

.label {
  display: block;
  font-size: 26rpx;
  color: #333;
  margin-bottom: 12rpx;
}

.input {
  height: 90rpx;
  border: 1px solid #ddd;
  border-radius: 14rpx;
  padding: 0 20rpx;
  font-size: 28rpx;
  background: #fafafa;
}

.btn {
  margin-top: 10rpx;
  height: 90rpx;
  line-height: 90rpx;
  border-radius: 14rpx;
  background: #1677ff;
  color: white;
  font-size: 30rpx;
  font-weight: 600;
}
</style>
