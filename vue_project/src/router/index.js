import Vue from 'vue'
import VueRouter from 'vue-router'
// 给相关的组件编写路由
import Login from '@/components/Login.vue'
import Home from '@/components/Home.vue'
import Register from '@/components/Register.vue'
import Welcome from '@/components/Welcome.vue'
import userList from '@/components/list/UserList.vue'
import musicList from '@/components/list/MusicList.vue'
import documentList from '@/components/list/DocumentList.vue'
import videoList from '@/components/list/VideoList.vue'
import pictureList from '@/components/list/PictureList.vue'
import storyList from '@/components/list/StoryList.vue'
import rubbishList from '@/components/list/RubbishList.vue'

Vue.use(VueRouter)

const routes = [
  { path: '/', redirect: '/login' }, // 重定向
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  {
    path: '/home',
    redirect: '/welcome',
    component: Home,
    children: [
      {
        path: '/welcome', component: Welcome
      },
      { path: '/users', component: userList },
      { path: '/musics', component: musicList },
      { path: '/videos', component: videoList },
      { path: '/pictures', component: pictureList },
      { path: '/documents', component: documentList },
      { path: '/stores', component: storyList },
      { path: '/rubbishs', component: rubbishList }
    ]
  }

]

const router = new VueRouter({
  routes
})
// 路由守卫
router.beforeEach(
  (to, from, next) => {
    if (to.path === '/login' || to.path === '/register') return next() // 注册、登录直接放行
    // 获取token令牌
    const tokenStr = window.sessionStorage.getItem('token')
    //! 可将变量转换成boolean类型，null、undefined和空字符串取反都为false，其余都为true。
    if (!tokenStr) return next('/login')// false
    next()
  })

export default router
