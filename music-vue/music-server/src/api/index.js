import {get,post} from "./http";
import Axios from 'axios';


//==========歌手相关==========
//查询歌手
export const getAllSinger = () => get('singer/allSinger');
//============歌曲相关============
//根据歌手id查询歌曲
export const songOfSingerId=(id)=>get(`song/singer/detail?singerId=${id}`);
//根据歌曲id查询歌曲对象
export const songOfSongId=(id)=>get(`song/detail?songId=${id}`);
//根据歌曲id查询歌曲对象
export const likeSongOfName=(keywords)=>get(`song/likeSongOfSongName?songName=${keywords}`);
//============歌单相关===============
//查询歌单
export const getAllSongList = () => get('songList/allSongList');
//==============歌单歌曲相关============
//根据歌单id查询
export const listSongDetail = (songListId) => get(`listSong/detail?songListId=${songListId}`);
//歌单列表包含文字
export const getSongListOflikeTitle = (keywords) => get(`songList/likeTitle?title=${keywords}`);
//===================用户相关===============
//查询用户
export const getAllConsumer = () => get('consumer/allConsumer');
//下载音乐
export const download=(url)=>Axios({
    method:'get',
    url:url,
    responseType:'blob'
});
//注册
export const SignUp=(params)=>post(`/consumer/add`,params);
//登录
export const LoginIn=(params)=>post(`/consumer/login`,params);
//根据id查询
export const getUserOfId = (id) => get(`consumer/selectByPrimaryKey?id=${id}`);
//更新
export const updateUserMsg=(params)=>post(`/consumer/update`,params);
