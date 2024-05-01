package com.example.music.service;

import com.example.music.domain.Song;
import com.example.music.domain.SongList;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SongListService {
    public boolean insert(SongList songList);
    //修改
    public boolean update(SongList songList);
    //删除
    public boolean delete(Integer id);
    //查询整个对象
    public SongList selectByPrimaryKey(Integer id);
    //查询所有歌曲
    public List<SongList> allSongList();
    //根据歌单名模糊查询
    public List<Song> likeTitle(String title);
    //根据歌单名精确查询
    public List<Song> songListOfTitle(String title);
    //根据风格名模糊查询
    public List<Song> likeStyle(String style);
}
