package com.kopo.memo.memo;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	
	// selectList
	@GetMapping("/")
	public String home(Model model) {
		DB db = new DB("memo");
		try {
			ArrayList<Memo> memo = new ArrayList<Memo>();
			memo = db.selectData();
			model.addAttribute("resultList", memo);
			model.addAttribute("btnName", "저장");
		} catch (Exception e) {
			
		}
		return "main";
	}
	
	// createTable
	@GetMapping("/create")
	public String create(Model model) {
		DB db = new DB("memo");
		try {
			db.createTable();
			model.addAttribute("msg", "테이블이 생성되었습니다.");
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
		}
		
		return "message";
	}
	
	// insert
	@ResponseBody
	@PostMapping("/insert_action")
	public String insert_action(HttpServletRequest request, Model model) {
		DB db = new DB("memo");

		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String msg = "";
		
		try {
			db.insertData(new Memo(title, contents));
			msg = "입력 되었습니다.";
			return msg;
		} catch (Exception e) {
			msg = "입력에 실패했습니다.";
			return msg;
		}
	}
	
	// detail
	@GetMapping("/detail_action")
	public String detail_action(Model model, @RequestParam("idx") int idx) {
		DB db = new DB("memo");
		
		try {
			
			ArrayList<Memo> memo = new ArrayList<Memo>();
			memo = db.selectData();
			model.addAttribute("resultList", memo);
			
			
			Memo selectDataOne = db.selectDataOne(idx);
			model.addAttribute("idx", selectDataOne.idx);
			model.addAttribute("title", selectDataOne.title);
			model.addAttribute("contents", selectDataOne.contents);
			model.addAttribute("btnName", "수정");
		} catch (Exception e) {
			
		}
		
		return "main";
	}
	
	// update
	@ResponseBody
	@PostMapping("/update_action")
	public String update_action(HttpServletRequest request, Model model) {
		DB db = new DB("memo");
		
		String idx = request.getParameter("idx");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String msg = "";
		
		try {
			db.updateData(idx, title, contents);
			msg = "수정 되었습니다.";
			return msg;
		} catch (Exception e) {
			msg = "수정에 실패했습니다.";
			return msg;
		}
	}
	
	// delete
	@ResponseBody
	@GetMapping("/delete_action")
	public String delete_action(Model model, @RequestParam("idx") int idx) {
		DB db = new DB("memo");
		
		String msg = "";
		
		try {
			db.deleteData(idx);
			msg = "삭제 되었습니다.";
			return msg;
		} catch (Exception e) {
			msg = "삭제에 실패했습니다.";
			return msg;
		}
	}
	
	// deleteAll
	@ResponseBody
	@GetMapping("/delete_all_action")
	public String delete_all_action(Model model) {
		DB db = new DB("memo");
		
		String msg = "";
		
		try {
			db.deleteAllData();
			msg = "전체 삭제 되었습니다.";
			return msg;
		} catch (Exception e) {
			msg = "전체 삭제에 실패했습니다.";
			return msg;
		}
	}
}
