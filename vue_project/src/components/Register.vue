<template>
  <!--整个页面-->
  <div class="register_container">
    <div class="snow-container">
      <div class="snow foreground"></div>
      <div class="snow foreground layered"></div>
      <div class="snow middleground"></div>
      <div class="snow middleground layered"></div>
      <div class="snow background"></div>
      <div class="snow background layered"></div>
    </div>
    <!--注册页面-->
    <div class="register_box">
      <!--图片-->
      <img src="../assets/logo.png" alt="" class="register_img" />
      <!--表单 2个输入框，2个按钮-->
      <el-form label-width="0px" ref="userFormRef" :model="user_form" :rules="userRules" class="register_form">
        <!--clearable可清空  用户名 prop属性校验字段名-->
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user" clearable v-model="user_form.username" placeholder="请输入用户名">
          </el-input>
        </el-form-item>
        <!--show-password密码框  密码 prop属性校验字段名-->
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" v-model="user_form.password" show-password placeholder="请输入密码">
          </el-input>
        </el-form-item>
        <!--按钮-->
        <el-form-item class="register_btn">
          <!--v-on:click===>事件绑定 简写：@click-->
          <el-button type="primary" v-on:click="register">注册</el-button>
          <el-button type="info" v-on:click="back">返回</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user_form: {
        username: '',
        password: ''
      },
      // 对用户名、密码进行校验
      userRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在3到10个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 15, message: '长度在6到15个字符', trigger: 'blur' }
        ]
      }
    }
  },
  // 点击事件实现跳转并传递参数的方法
  methods: {
    register() {
      // 表单数据  userFormRef--给表单打的标签名
      this.$refs.userFormRef.validate(async (valid) => {
        if (!valid) return // false
        const { data: res } = await this.$http.post('register', this.user_form) // 接收通过post方式传输过来的用户名、密码
        // 判断
        // console.log(res);
        if (res.code != 200) return this.$message.error(res.msg) // 如果状态码不是200,返回提示信息  --拿到后端编写的提示信息
        this.$message.success(res.msg) // 注册成功
        this.$router.push('/login')
      })
    },
    back() {
      this.$router.push('/login')
    }
  }
}
</script>

<style lang="less" scoped>
//整个页面
.snow-container {
  position: absolute;
  height: 100%;
  width: 100%;
  max-width: 100%;
  top: 0;
  overflow: hidden;
  z-index: 2;
  pointer-events: none;
  background-color: #203244;
  opacity: 0.2;
}

.snow-container.two {
  height: 440px;
}

.snow {
  display: block;
  position: absolute;
  z-index: 2;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  pointer-events: none;
  -webkit-transform: translate3d(0, -100%, 0);
  transform: translate3d(0, -100%, 0);
  -webkit-animation: snow linear infinite;
  animation: snow linear infinite;
}

.snow.foreground {
  background-image: url("../assets/1.png");
  -webkit-animation-duration: 15s;
  animation-duration: 15s;
}

.snow.foreground.layered {
  -webkit-animation-delay: 7.5s;
  animation-delay: 7.5s;
}

.snow.middleground {
  background-image: url("../assets/2.png");
  -webkit-animation-duration: 20s;
  animation-duration: 20s;
}

.snow.middleground.layered {
  -webkit-animation-delay: 10s;
  animation-delay: 10s;
}

.snow.background {
  background-image: url("../assets/3.png");
  -webkit-animation-duration: 30s;
  animation-duration: 30s;
}

.snow.background.layered {
  -webkit-animation-delay: 15s;
  animation-delay: 15s;
}

@-webkit-keyframes snow {
  0% {
    -webkit-transform: translate3d(0, -100%, 0);
    transform: translate3d(0, -100%, 0);
  }

  100% {
    -webkit-transform: translate3d(15%, 100%, 0);
    transform: translate3d(15%, 100%, 0);
  }
}

@keyframes snow {
  0% {
    -webkit-transform: translate3d(0, -100%, 0);
    transform: translate3d(0, -100%, 0);
  }

  100% {
    -webkit-transform: translate3d(15%, 100%, 0);
    transform: translate3d(15%, 100%, 0);
  }
}

@media (max-width: 1366px) {
  .snow-container {
    position: absolute;
    height: 628px;
  }
}

@media (max-width: 1280px) {
  .snow-container {
    position: absolute;
    height: 578px;
  }
}

@media (max-width: 1080px) {
  .snow-container {
    position: absolute;
    height: 498px;
  }
}

@media (max-width: 1054px) {}

@media (max-width: 1024px) {}

@media (max-width: 991px) {
  .snow-container.two {
    height: 365px;
  }
}

@media (max-width: 800px) {
  .snow-container {
    position: absolute;
    height: 375px;
  }

  .snow-container.two {
    height: 352px;
  }
}

@media (max-width: 768px) {
  .snow-container {
    position: absolute;
    height: 373px;
  }
}

@media (max-width: 640px) {}

@media (max-width: 480px) {}

@media (max-width: 414px) {}

@media (max-width: 384px) {}

.register_container {
  //background-color: #487c7a;
  height: 100%;
  position: relative;
  background-image: url(../assets/bg1.jpeg);
  background-size: 100% 100%; //
}

//登录页面
.register_box {
  width: 450px;
  height: 300px;
  background-color: #fff;
  position: absolute; //绝对定位，脱离文档流
  top: 50%;
  left: 50%;
  //translate()中的x和y来表示元素在水平方向或者垂直方向上移动
  transform: translate(-50%, -50%);
  border-radius: 5px; //圆角
  z-index: 2;
}

//图片
.register_img {
  height: 100px;
  margin-left: 150px;
  margin-top: 20px;
}

//表单
.register_form {
  margin-left: 20px;
  margin-right: 20px;
}

.register_btn {
  margin-left: 260px;
}</style>
