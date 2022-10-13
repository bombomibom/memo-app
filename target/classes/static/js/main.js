var $btn_set = $('#btn_set'), // 저장 버튼 객체
	$btn_remove = $('#btn_remove'),				// 삭제 버튼 객체
	$btn_clear = $('#btn_clear'),		// 모두삭제 버튼 객체
	$memo = $('#memo');			 // 글상자 객체
var storage_length;		// 저장목록 개수

// 저장소에 저장
function set_storage(key, value) {
	localStorage.setItem(key, value); // 저장소에 저장
	list_storage(); // 저장소 목록 업데이트 
}

// 저장소 모두 삭제
function clear_storage() {
	var q = confirm('정말로 모든 데이타를 삭제하시겠습니까?');
	if (q) {
		localStorage.clear();
		alert('저장소의 모든 내용을 삭제했습니다');
		list_storage();
	}
}

// 특정 키 삭제
function remove_storage(key) {
	var q = confirm(key + ' 데이타를 삭제하시겠습니까?');
	if (q) {
		// 해당 키값의 데이터 삭제
		localStorage.removeItem(key);
		list_storage();
		alert(key + ' 를 삭제했습니다');
	}
}

// 저장소 목록 조회(key)
function list_storage() {
	var key;
	storage_length = localStorage.length; // 총 저장소 길이
	$('#list > ol').empty();
	for (var i = 0; i < storage_length; i++) {
		key = localStorage.key(i);
		$('#list > ol').append('<li>' + key + '</li>').find('li');
	}
}

// 저장 클릭
$btn_set.click(function() {
	var key_name, data;

	// 현재 선택한 파일명(자동 파일명 선택)
	var name = $('#list').find('li').filter('.selected').text();

	// 저장할 데이터의  키값(이름)을 입력 받음
	key_name = prompt("저장할 데이터의 제목을 입력하세요", name);
	data = $memo.val();
	console.log("value= " + data);
	set_storage(key_name, data);
	console.log(data);
});

// 모두 삭제 클릭
$btn_clear.click(function() {
	clear_storage();
});

// 삭제 클릭
$('#btn_remove').on('click', function() {
	// 선택한 목록의 text(key값)을 조회
	var key = $('#list').find('li').filter('.selected').text();
	console.log(key);
	var q = confirm(key + " 을 삭제하시겠습니까?");
	if (q) {
		remove_storage(key); // 삭제 함수 호출	
	}

});

// 저장목록 클릭
// 보기(동적 생성 요소의 이벤트 처리는 on 메소드 사용)
$('#list').on('click', 'ol li', function() {
	var key = $(this).text();
	$('#list li').removeClass('selected'); // 선택효과 초기화
	$(this).addClass('selected'); // 선택효과

	// 메모장에 데이터 출력
	$('#memo').val(localStorage.getItem(key));
});


(function() {
	// 저장소 지원 여부 확인
	if (typeof (Storage) !== 'undefined') {
		storage_length = localStorage.length; // 총 저장소길이
		list_storage();	// 목록 조회
	} else {
		alert('웹 저장소(local Storage)를 지원하지 않습니다');
	}
})();