package com.example.music.service;

import com.example.music.domain.ListSong;

import java.util.List;

public interface ListSongService {
    //增加
    public boolean insert(ListSong listSong);
    //修改
    public boolean update(ListSong listSong);
    //删除
    public boolean delete(Integer id);
    //查询整个对象
    public ListSong selectByPrimaryKey(Integer id);
    //查询所有歌单歌曲
    public List<ListSong> allListSong();
    //根据歌单id查询
    public List<ListSong> listSongOfSongListId(Integer songListId);
    //根据歌曲id和歌单id删除
    public boolean deleteBySongIdAndSongListId(Integer songId,Integer songListId);
}
