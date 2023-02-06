package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.model.dto.ReplySaveRequestDto;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private UserRepository userRepository;
	

	//서비스가 성공하면 commit, 실피하면 rollback
	@Transactional
	public void 글쓰기(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글상세보기 실패 : 글 아이디를 찾을 수 없습니다.");
				});
	}

	@Transactional
	public void 삭제하기(int id) {
		 boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 글 아이디를 찾을 수 없습니다.");
				}); //영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수 종료시(Service가 종료 될때) 트랜잭션이 종료됨, 이때 더티체킹 - 자동 업데이트. flush
	}

	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replysaveRequestDto) {
		
		User user =userRepository.findById(replysaveRequestDto.getUserId())
				.orElseThrow(()->{
					return new IllegalArgumentException("댓글 쓰기 실패 : 유저id를 찾을 수 없습니다.");
				});
		
		Board board = boardRepository.findById(replysaveRequestDto.getBoardId())
				.orElseThrow(()->{
					return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 id를 찾을 수 없습니다.");
				});
		
		Reply reply = Reply.builder()
				.user(user)
				.board(board)
				.content(replysaveRequestDto.getContent())
				.build();
		
		replyRepository.save(reply);
	}


}
