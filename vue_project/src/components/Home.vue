<template>
  <el-container>
    <!-- 头部 -->
    <el-header>
      <div>
        <span>凌云网盘</span>
      </div>
      <el-button type="info" @click="loginOut">退出</el-button>
    </el-header>
    <el-container>
      <!-- 侧边栏 -->
      <el-aside width="200px">
        <el-menu
          background-color="#333744"
          text-color="#fff"
          active-text-color="#409EFF"
          unique-opened
          router
          :default-active="activePath"
        ><!-- :default-active="activePath"高亮显示-->
        <!--:index="'/users'" 路径跳转-->
          <el-menu-item @click="saveNavstatu('/users')" :index="'/users'">
            <i class="el-icon-s-cooperation"></i>
            <span slot="title">所有文件</span>
          </el-menu-item>

          <el-menu-item @click="saveNavstatu('/videos')" :index="'/videos'">
            <i class="el-icon-video-camera"></i>
            <span slot="title">视频</span>
          </el-menu-item>

          <el-menu-item @click="saveNavstatu('/musics')" :index="'/musics'">
            <i class="el-icon-headset"></i>
            <span slot="title">音乐</span>
          </el-menu-item>

          <el-menu-item @click="saveNavstatu('/pictures')" :index="'/pictures'">
            <i class="el-icon-picture"></i>
            <span slot="title">图片</span>
          </el-menu-item>

          <el-menu-item @click="saveNavstatu('/documents')" :index="'/documents'">
            <i class="el-icon-document"></i>
            <span slot="title">文档</span>
          </el-menu-item>

          <el-menu-item @click="saveNavstatu('/stores')" :index="'/stores'">
            <i class="el-icon-receiving"></i>
            <span slot="title">收藏</span>
          </el-menu-item>

          <el-menu-item @click="saveNavstatu('/rubbishs')" :index="'/rubbishs'">
            <i class="el-icon-delete"></i>
            <span slot="title">回收站</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <!--路由占位符-->
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data () {
    return {
      activePath: ''
    }
  },
  methods: {
    loginOut () {
      // 当session结束时，清除token
      window.sessionStorage.clear()
      this.$router.push('/login')
    },
    // 高亮显示
    saveNavstatu (activePath) {
      window.sessionStorage.setItem('activePath', activePath)
      this.activePath = activePath
    }
  }
}
</script>

<style lang="less" scoped>
.el-container {
  height: 100%;
}
.el-header {
  background-color: #26475c;
  display: flex;
  justify-content: space-between;
  padding-left: 0;
  align-items: center;
  color: #fff;
  font-size: 20px;
  > div {
    display: flex;
    align-items: center;
    span {
      margin-left: 15px;
    }
  }
}

.el-aside {
  background-color: #333744;
  .el-menu {
    border-right: none;
  }
}

.el-main {
 background-color: #eaedf1;
}

.iconfont {
  margin-right: 10px;
}

.toggle-button {
  background-color: #4a5064;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;
}
</style>
