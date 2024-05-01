import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from "@/views/Home";
import MyMusic from "@/views/MyMusic";
import Singer from "@/views/Singer";
import SongList from "@/views/SongList";
import Search from "@/views/Search";
import Lyric from "@/views/Lyric";
import LoginIn from "@/views/LoginIn";
import SignUp from "@/views/SignUp";
import Setting from "@/views/Setting";
import SingerAlbm from "@/views/SingerAlbum";
import SongListAlbum from "@/views/SongListAlbum";
import Info from "@/components/Info";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/my-music',
    name: 'my-music',
    component: MyMusic
  },
  {
    path: '/singer',
    name: 'singer',
    component: Singer
  },
  {
    path: '/song-list',
    name: 'song-list',
    component: SongList
  },
  {
    path: '/search',
    name: 'Search',
    component: Search
  },
  {
    path: '/lyric',
    name: 'Lyric',
    component: Lyric
  },
  {
    path: '/login-in',
    name: 'LoginIn',
    component: LoginIn
  },
  {
    path: '/sign-up',
    name: 'SignUp',
    component: SignUp
  },
  {
    path: '/setting',
    name: 'setting',
    component: Setting
  },
  {
    path: '/singer-album/:id',
    name: 'SingerAlbum',
    component: SingerAlbm
  },
  {
    path: '/song-list-album/:id',
    name: 'SongListAlbum',
    component: SongListAlbum
  },
]

const originaPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originaPush.call(this, location).catch(err => err)
}

const router = new VueRouter({
  routes
})

export default router
