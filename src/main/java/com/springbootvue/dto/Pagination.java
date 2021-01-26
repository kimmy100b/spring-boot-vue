package com.springbootvue.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Pagination {
    /** 한 페이지당 게시글 수 **/
    private int pageSize = 10;

    /** 한 블럭(range)당 페이지 수 **/
    private int rangeSize = 5;

    /** 현재 페이지 **/
    private int curPage = 1;

    /** 현재 블럭(range) **/
    private int curRange = 1;

    /** 총 게시글 수 **/
    private int listCnt;

    /** 총 페이지 수 **/
    private int pageCnt;

    /** 총 블럭(range) 수 **/
    private int rangeCnt;

    /** 시작 페이지 **/
    private int startPage = 1;

    /** 끝 페이지 **/
    private int endPage = 1;

    /** 시작 index **/
    private int startIndex = 0;

    /** 끝 index **/
    private int endIndex = 0;

    /** 이전 페이지 **/
    private int prePage;

    /** 다음 페이지 **/
    private int nextPage;

    public Pagination(int curPage, int listCnt) {
        setCurPage(curPage);
        setListCnt(listCnt);

        setPageCnt(listCnt);
        setRangeCnt(this.pageCnt);

        rangSetting(curPage);

        setStartIndex(curPage);
        setEndIndex(curPage);
    }

    public void setPageCnt(int listCnt) {
        this.pageCnt = (int) Math.ceil(listCnt*1.0/pageSize);
    }

    public void setRangeCnt(int pageCnt) {
        this.rangeCnt = (int)Math.ceil(pageCnt*1.0/rangeSize);
    }

    public void setCurRange(int curPage){
        this.curRange = (int)((curPage-1)/rangeSize)+1;
    }

    public void rangSetting(int curPage){
        setCurRange(curPage);
        this.startPage = (curRange - 1) * rangeSize + 1;
        this.endPage = startPage + rangeSize - 1;

        if(endPage > pageCnt){
            this.endPage = pageCnt;
        }

        this.prePage = curPage - 1;
        this.nextPage = curPage + 1;
    }

    public void setStartIndex(int curPage){

        this.startIndex = (curPage-1)*pageSize;
    }

    public void setEndIndex(int curPage) {
        this.endIndex = curPage*pageSize-1;
    }
}
