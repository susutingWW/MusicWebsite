import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path:'/',
    redirect:'/login'
  },
  {
    path: '/login',
    component: () => import('../pages/Login.vue')
  },
  {
    path: '/home',
    component: () => import('../components/Home.vue'),
    children:[
      {
        path: '/info',
        component: () => import('../pages/InfoPage.vue')
      },
      {
        path: '/consumer',
        component: () => import('../pages/ConsumerPage.vue')
      },
      {
        path: '/singer',
        component: () => import('../pages/SingerPage.vue')
      },
      {
        path: '/songList',
        component: () => import('../pages/SongListPage.vue')
      },
      {
        path: '/song',
        component: () => import('../pages/SongPage.vue')
      },
      {
        path: '/listSong',
        component: () => import('../pages/ListSongPage.vue')
      }
    ]
  },
]

const router = new VueRouter({
  routes
})

export default router
