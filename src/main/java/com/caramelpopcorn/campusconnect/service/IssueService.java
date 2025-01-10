package com.caramelpopcorn.campusconnect.service;

import com.caramelpopcorn.campusconnect.dto.IssueReqDto;
import com.caramelpopcorn.campusconnect.dto.IssueResDto;
import com.caramelpopcorn.campusconnect.entity.Issue;
import com.caramelpopcorn.campusconnect.global.State;
import com.caramelpopcorn.campusconnect.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueService {
    private final IssueRepository issueRepository;
    private final CommentService commentService;
    private final EmpathyService empathyService;

    public Issue createIssue(IssueReqDto issueReqDto, String writer) {
        Issue issue = Issue.builder()
                .title(issueReqDto.getTitle())
                .category(issueReqDto.getCategory())
                .content(issueReqDto.getContent())
                .solution(issueReqDto.getSolution())
                .state(issueReqDto.getState())
                .vote_link(issueReqDto.getVote_link())
                .priority(0.0) // priority는 일단 0.0
                .created(issueReqDto.getCreated())
                .build();
        return issueRepository.save(issue);
    }

    public List<IssueResDto> getAllIssues() {
        List<Issue> issues = issueRepository.findAll();

        return issues.stream().map(issue -> {
            IssueResDto issueResDto = new IssueResDto();
            issueResDto.setTitle(issue.getTitle());
            issueResDto.setCategory(issue.getCategory());
            issueResDto.setContent(issue.getContent());
            issueResDto.setSolution(issue.getSolution());
            issueResDto.setState(issue.getState());
            issueResDto.setVote_link(issue.getVote_link());
            issueResDto.setCreated(issue.getCreated());
            issueResDto.setEmpathyCount(empathyService.getEmpathyCountByIssueId(issue.getId()));
            issueResDto.setCommentCount(commentService.getCommentCountByIssueId(issue.getId())); // 댓글 수
            issueResDto.setVoteCount(null);
            return issueResDto;
        }).collect(Collectors.toList());
    }

    public Issue getIssueById(Long id) {
        return issueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID에 해당하는 이슈를 찾을 수 없습니다: " + id));
    }

    public Issue updateIssueState(Long id, State state) {

        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID에 해당하는 이슈를 찾을 수 없습니다: " + id));

        issue.setState(state);

        return issueRepository.save(issue);
    }

    public List<Issue> test1() {
        List<Issue> issueList = new ArrayList<>(
                List.of(
                        new Issue("user1", "I one pass 인증", "전산", "인증 기관코드 발급코드 휴대폰에 i-one pass 어플을 설치했으나 바로 기관코드와 아이디, 발급코드를 입력하라고 하는데 어떻게 코드를 알 수 있을까요? 휴대폰을 바꿔서 재발급 받고자 합니다", "기관코드와 발급코드는 학사 담당자에게 문의하세요.", State.IN_PROGRESS, 48, 12, 84.3, LocalDateTime.parse("2024-09-09T07:56:00")),
                        new Issue("user2","학교생활&진로 관련", "학사", "전반적인 학교생활이나 진로에 대해서 직접 상담을 받을 수 있는 곳이 있을까요?", "학생 상담실에서 도움을 받을 수 있습니다.", State.NOT_STARTED, 20, 4, 61.3, LocalDateTime.parse("2024-10-04T03:48:00")),
                        new Issue("user1", "i-one-pass 통합로그인 2차인증발급을 예전에 했었는데요", "전산", "통합정보시스템 2차인증코드발급 예전에 앱을 잘 설치해서 2차인증발급을 예전에 했었는데요 앱을 실수로 삭제했다가 다시 깔았는데 코드번호를 모르겠는데 어디서 확인해야 할까요?", "학교 포털에서 발급코드를 확인하거나 재발급 요청하세요.", State.NOT_STARTED, 34, 8, 78.5, LocalDateTime.parse("2024-08-11T23:19:00")),
                        new Issue("user1", "i-one pass에 관해 질문드립니다.", "전산", "전산 i-one pass 어플을 깔아서 로그인을 할려고 들어왔는데 기관코드와 발급코드를 작성해야한다고 나와있습니다. 하지만 저는 이와 관련된 메일이나 문자를 받은 기억이 없는데 혹시 어디서 확인가능할까요?", "기관코드 및 발급코드는 포털 공지나 학사팀에 문의하세요.", State.DONE, 28, 6, 66.7, LocalDateTime.parse("2024-07-15T19:04:00")),
                        new Issue("user3","샬롬 601호 뒷문이 너무 쾅쾅 닫힙니다", "시설", "샬롬관 문 샬롬관 조치 부탁드립니다...", "문 소음을 줄이는 대책을 논의 중입니다.", State.NOT_STARTED, 5, 0, 40.2, LocalDateTime.parse("2024-03-18T13:45:00")),
                        new Issue("user3","교육관 시설 보수 관련하여 문의 드립니다.", "시설", "천장형에어컨 교육관시설보수 안녕하세요. 교육관 건물 에어컨 시설 관련하여 건의드릴게 있어 연락 드렸습니다...", "에어컨 바람막이 설치 요청을 검토 중입니다.", State.IN_PROGRESS, 22, 8, 88.9, LocalDateTime.parse("2023-09-20T20:21:00")),
                        new Issue("user3","주차 정기권 기간 중 차량 변경", "시설", "정기권 차량변경 주차권 학기 정기권이 끊어져있는 상태인데 도중에 차량을 변경하게 되었습니다...", "차량 변경 절차를 학생 포털에서 확인하세요.", State.DONE, 10, 2, 55.7, LocalDateTime.parse("2023-05-25T18:42:00")),
                        new Issue("user3","도서관 소등, 냉난방 관련 문의드립니다.", "시설", "도서관 소등, 냉난방 도서관 오후시간에 부분소등을 하는 이유는 무엇인가요?...", "소등 및 냉난방 운영 정책을 검토 중입니다.", State.IN_PROGRESS, 12, 3, 65.1, LocalDateTime.parse("2023-04-26T18:50:00")),
                        new Issue("user1", "i one pass 2차 통합 로그인 관련해서 문의드립니다.", "전산", "로그인 정합정보시스템 핸드폰을 바꾸는 과정에서 i one pass가 삭제되었는데 그 이후로 매번 정합정보시스템에 들어가기 위해서는 매번 이메일을 통해 비밀번호를 확인하고 로그인 해야하는 번거로움을 거쳐야 했습니다.", "포털에서 i one pass를 재설치하고 발급코드를 재신청하세요.", State.REJECTED, 15, 3, 54.2, LocalDateTime.parse("2024-07-10T01:27:00")),
                        new Issue("user1", "핸드폰 변경 후 통합로그인 2차 인증", "전산", "통합로그인 2차인증 안녕하세요, 핸드폰 변경 후 i-one pass 등록이 해지되었습니다. 기관코드와 아이디, 발급코드를 입력해야 해서 학교 홈페이지 비밀번호 변경을 해봤지만 발급코드가 따로 오지 않아서 문의드립니다.", "발급코드 재신청은 포털에서 가능합니다.", State.IN_PROGRESS, 43, 10, 89.5, LocalDateTime.parse("2024-07-03T23:03:00")),
                        new Issue("user1", "pc에서 통합로그인 문제", "전산", "통합로그인 pc pc에서 통합로그인이 몇 달째 안되는데, 해결방법 있을까요?", "학교 IT 지원팀에 문의하여 로그인 문제를 해결하세요.", State.NOT_STARTED, 22, 5, 73.1, LocalDateTime.parse("2024-06-21T17:42:00")),
                        new Issue("user1", "이러닝 캠퍼스, 코스모스 접속 불가", "전산", "접속 불가 접속불가 6/20(수) 이러닝 캠퍼스, 코스모스 접속이 불가합니다. 과제 제출, 계절학기 수업을 들어야하는데 확인해 주시거나 전화연결이 되었으면 합니다.", "IT 지원팀에 연락하여 접속 문제를 해결하세요.", State.IN_PROGRESS, 50, 15, 80.4, LocalDateTime.parse("2024-06-20T19:18:00")),
                        new Issue("user2", "종합정보시스템 학적 관리 자격증 조회 관련 문의", "전산", "종합정보시스템 학적관리 자격증 조회 종합정보시스템 학적관리에서 자격증 조회란이 있는데 이거는 어떤 것인가요? 따로 추가 하는 건 안 보이던데요..", "학사 담당자에게 자격증 관리 옵션에 대해 문의하세요.", State.DONE, 12, 2, 55.7, LocalDateTime.parse("2024-06-18T11:28:00")),
                        new Issue("user2","어학연수 관련 질문", "학사", "2. 어학연수 신청방법이 궁금합니다.", "어학연수 신청 방법은 학사 공지를 확인하세요.", State.IN_PROGRESS, 30, 8, 75.4, LocalDateTime.parse("2024-10-07T17:23:00")),
                        new Issue("user2","건의 사항: 샬롬관 709호 강의실 의자 교체 요청", "시설", "건의 사항: 샬롬관 709호 강의실 의자 교체 요청", "교체 요청 검토 중입니다.", State.IN_PROGRESS, 37, 15, 78.2, LocalDateTime.parse("2024-10-02T13:46:00")),
                        new Issue("user2","강의실 환경 개선을 위한 신식 에어컨 설치 건의", "시설", "저는 정경학부에 재학 중인 장상두입니다. 오늘 저는 강의실의 학습 환경 개선을 위해 건의드리고자 합니다.", "신식 에어컨 설치 요청 검토 중입니다.", State.IN_PROGRESS, 50, 20, 82.1, LocalDateTime.parse("2024-10-01T12:43:00")),
                        new Issue("user2","휴학 관련 문의", "학사", "학교 휴학 관련 문의 메일은 어디다 보낼수 있을까요?", "학사 담당 이메일을 확인하세요.", State.DONE, 18, 5, 50.8, LocalDateTime.parse("2024-09-27T17:40:00")),
                        new Issue("user2","졸업평가 면제조건", "학사", "졸업평가 면저 글경 19학번인데 졸업평가 면제조건이 어떻게되나요", "졸업평가 면제조건은 학사 공지를 참고하세요.", State.NOT_STARTED, 25, 8, 65.4, LocalDateTime.parse("2024-09-26T15:08:00")),
                        new Issue("user2","취업계 관련 유고결석 문의 건", "학사", "취업 유고결석 안녕하세요, 경제세무학과 4학년 201982072 서보혁입니다. 다름이 아니오라, 8학기 초과등록자인데 유고결석(취업계) 가능할까요?", "유고결석 처리 관련 담당자에게 문의하세요.", State.IN_PROGRESS, 40, 12, 89.6, LocalDateTime.parse("2024-09-11T14:20:00")),
                        new Issue("user2","졸업이수학점 관련 질문입니다", "학사", "학점 졸업 학점 졸업이수학점 관련 질문입니다. https://web.kangnam.ac.kr/menu/c5dc4b1d7b4dd402e5e6a7a8471eb55c.do?encMenuSeq=eb3b60b9e551de71300d75bf7894c98d", "입학년도 기준으로 학점이 계산됩니다.", State.DONE, 28, 6, 70.2, LocalDateTime.parse("2024-09-09T18:54:00")),
                        new Issue("user2", "예비졸업심사 관련", "학사", "졸업심사 학점 19년도 입학생이라 심화전공 선택해서 전공기초6학점+전공선택60학점채워야되는데...", "예비졸업심사 기준 확인 중입니다.", State.IN_PROGRESS, 35, 10, 75.9, LocalDateTime.parse("2024-09-09T15:11:00")),
                        new Issue("user3","I one pass 인증", "전산", "인증 기관코드 발급코드 휴대폰에 i-one pass 어플을 설치했으나 바로 기관코드와 아이디, 발급코드를 입력하라고 하는데...", "기관코드 발급 절차를 확인 중입니다.", State.REJECTED, 9, 2, 40.3, LocalDateTime.parse("2024-09-09T07:56:00")),
                        new Issue("user3","강의실 환경 개선을 위한 신식 에어컨 설치 건의", "시설", "에어컨 샬롬관 안녕하세요, 저는 정경학부에 재학 중인 장상두입니다. 오늘 저는 강의실의 학습 환경 개선을 위해 건의드리고자 합니다. 현재 저희 대학교 강의실의 에어컨이 오래되어 제 기능을 다하지 못하고 있습니다. 특히, 강의실에 많은 학생들이 몰리다 보니 더위가 심하게 느껴집니다...", "신식 에어컨 설치 요청을 검토 중입니다.", State.IN_PROGRESS, 30, 10, 85.3, LocalDateTime.parse("2024-10-01T12:43:00")),
                        new Issue("user3","경천관 1층 정수기 교체 관련 문의", "시설", "정수기 교체 관련 문의 경천관 1층 정수기 언제나 노고 많으십니다. 다름이 아니라 건의드리고 싶은 사항이 있어 글을 남깁니다...", "정수기 교체 가능 여부를 확인 중입니다.", State.IN_PROGRESS, 15, 4, 72.4, LocalDateTime.parse("2024-08-22T15:28:00")),
                        new Issue("user3","기숙사 신청", "시설", "가숙사 신청 2학기 기숙사 신청기간 언제까지 인가요?", "기숙사 신청기간은 학생 포털 공지를 참고하세요.", State.DONE, 8, 1, 34.8, LocalDateTime.parse("2024-06-24T12:58:00")),
                        new Issue("user3","샬롬 601호 뒷문이 너무 쾅쾅 닫힙니다", "시설", "샬롬관 문 샬롬관 조치 부탁드립니다...", "문 소음을 줄이는 대책을 논의 중입니다.", State.NOT_STARTED, 5, 0, 40.2, LocalDateTime.parse("2024-03-18T13:45:00")),
                        new Issue("user3","교육관 시설 보수 관련하여 문의 드립니다.", "시설", "천장형에어컨 교육관시설보수 안녕하세요. 교육관 건물 에어컨 시설 관련하여 건의드릴게 있어 연락 드렸습니다...", "에어컨 바람막이 설치 요청을 검토 중입니다.", State.IN_PROGRESS, 22, 8, 88.9, LocalDateTime.parse("2023-09-20T20:21:00")),
                        new Issue("user3","주차 정기권 기간 중 차량 변경", "시설", "정기권 차량변경 주차권 학기 정기권이 끊어져있는 상태인데 도중에 차량을 변경하게 되었습니다...", "차량 변경 절차를 학생 포털에서 확인하세요.", State.DONE, 10, 2, 55.7, LocalDateTime.parse("2023-05-25T18:42:00")),
                        new Issue("user3","도서관 소등, 냉난방 관련 문의드립니다.", "시설", "도서관 소등, 냉난방 도서관 오후시간에 부분소등을 하는 이유는 무엇인가요?...", "소등 및 냉난방 운영 정책을 검토 중입니다.", State.IN_PROGRESS, 12, 3, 65.1, LocalDateTime.parse("2023-04-26T18:50:00")),
                        new Issue("user3","샤워실 위치", "시설", "현재 샤워실 사용이 가능 한 위치가 어디인가요 샤워실 위치 학교에서 샤워할 일이 생길 것 같은데...", "기숙사를 제외한 샤워실 위치를 시설팀에 문의하세요.", State.DONE, 7, 1, 28.6, LocalDateTime.parse("2023-04-25T20:10:00")),
                        new Issue("user3","도서관 너무 추워요. 도서관 난방 부탁드립니다.", "시설", "도서관 난방 공부할 때 시설이 너무 추워서 난방 부탁드립니다.", "난방 운영 시간을 조정 중입니다.", State.REJECTED, 4, 0, 19.2, LocalDateTime.parse("2023-04-06T17:44:00")),
                        new Issue("user3","기숙사 헬스장 운영 관련하여 문의합니다.", "시설", "생활건강헬스센터 학교 시설 운영 문의 기숙사 비에 헬스장 운영비용도 포함되어 있는 것으로 알고 있는데...", "헬스장 운영비와 개선 계획을 확인 중입니다.", State.IN_PROGRESS, 20, 7, 91.7, LocalDateTime.parse("2023-04-05T22:53:00"))

                ));
        return issueList;
    }
    public List<Issue> test2() {
        List<Issue> issueList = new ArrayList<>(
                List.of(
                        new Issue("user1", "전산 관련 불편 사항", "전산", "학생들은 i-one pass를 이용한 인증 과정에서 기관코드와 발급코드를 어떻게 확인해야 하는지에 대한 혼란을 겪고 있습니다. 휴대폰을 바꾸거나 앱을 삭제한 경우 해당 코드를 어떻게 얻을 수 있는지에 대한 안내가 부족하다는 점이 불편함을 느끼는 요인으로 작용하고 있습니다. 로그인 절차가 간소화되지 않아 번거로움을 느끼는 학생들이 많아, 이에 대한 개선이 필요해 보입니다.", "기관코드와 발급코드는 학사 담당자에게 문의하세요.", State.IN_PROGRESS, 48, 125, 391.5, LocalDateTime.parse("2025-01-11T04:26:03")),
                        new Issue("user2","학교생활&진로 관련", "학사", "전반적인 학교생활이나 진로에 대해서 직접 상담을 받을 수 있는 곳이 있을까요?", "학생 상담실에서 도움을 받을 수 있습니다.", State.NOT_STARTED, 20, 4, 61.3, LocalDateTime.parse("2024-10-04T03:48:00")),
                        new Issue("user3","샬롬 601호 뒷문이 너무 쾅쾅 닫힙니다", "시설", "샬롬관 문 샬롬관 조치 부탁드립니다...", "문 소음을 줄이는 대책을 논의 중입니다.", State.NOT_STARTED, 5, 0, 40.2, LocalDateTime.parse("2024-03-18T13:45:00")),
                        new Issue("user3","교육관 시설 보수 관련하여 문의 드립니다.", "시설", "천장형에어컨 교육관시설보수 안녕하세요. 교육관 건물 에어컨 시설 관련하여 건의드릴게 있어 연락 드렸습니다...", "에어컨 바람막이 설치 요청을 검토 중입니다.", State.IN_PROGRESS, 22, 8, 88.9, LocalDateTime.parse("2023-09-20T20:21:00")),
                        new Issue("user3","주차 정기권 기간 중 차량 변경", "시설", "정기권 차량변경 주차권 학기 정기권이 끊어져있는 상태인데 도중에 차량을 변경하게 되었습니다...", "차량 변경 절차를 학생 포털에서 확인하세요.", State.DONE, 10, 2, 55.7, LocalDateTime.parse("2023-05-25T18:42:00")),
                        new Issue("user3","도서관 소등, 냉난방 관련 문의드립니다.", "시설", "도서관 소등, 냉난방 도서관 오후시간에 부분소등을 하는 이유는 무엇인가요?...", "소등 및 냉난방 운영 정책을 검토 중입니다.", State.IN_PROGRESS, 12, 3, 65.1, LocalDateTime.parse("2023-04-26T18:50:00")),
                        new Issue("user1", "핸드폰 변경 후 통합로그인 2차 인증", "전산", "통합로그인 2차인증 안녕하세요, 핸드폰 변경 후 i-one pass 등록이 해지되었습니다. 기관코드와 아이디, 발급코드를 입력해야 해서 학교 홈페이지 비밀번호 변경을 해봤지만 발급코드가 따로 오지 않아서 문의드립니다.", "발급코드 재신청은 포털에서 가능합니다.", State.IN_PROGRESS, 43, 10, 89.5, LocalDateTime.parse("2024-07-03T23:03:00")),
                        new Issue("user1", "pc에서 통합로그인 문제", "전산", "통합로그인 pc pc에서 통합로그인이 몇 달째 안되는데, 해결방법 있을까요?", "학교 IT 지원팀에 문의하여 로그인 문제를 해결하세요.", State.NOT_STARTED, 22, 5, 73.1, LocalDateTime.parse("2024-06-21T17:42:00")),
                        new Issue("user1", "이러닝 캠퍼스, 코스모스 접속 불가", "전산", "접속 불가 접속불가 6/20(수) 이러닝 캠퍼스, 코스모스 접속이 불가합니다. 과제 제출, 계절학기 수업을 들어야하는데 확인해 주시거나 전화연결이 되었으면 합니다.", "IT 지원팀에 연락하여 접속 문제를 해결하세요.", State.IN_PROGRESS, 50, 15, 80.4, LocalDateTime.parse("2024-06-20T19:18:00")),
                        new Issue("user2", "종합정보시스템 학적 관리 자격증 조회 관련 문의", "전산", "종합정보시스템 학적관리 자격증 조회 종합정보시스템 학적관리에서 자격증 조회란이 있는데 이거는 어떤 것인가요? 따로 추가 하는 건 안 보이던데요..", "학사 담당자에게 자격증 관리 옵션에 대해 문의하세요.", State.DONE, 12, 2, 55.7, LocalDateTime.parse("2024-06-18T11:28:00")),
                        new Issue("user2","어학연수 관련 질문", "학사", "2. 어학연수 신청방법이 궁금합니다.", "어학연수 신청 방법은 학사 공지를 확인하세요.", State.IN_PROGRESS, 30, 8, 75.4, LocalDateTime.parse("2024-10-07T17:23:00")),
                        new Issue("user2","건의 사항: 샬롬관 709호 강의실 의자 교체 요청", "시설", "건의 사항: 샬롬관 709호 강의실 의자 교체 요청", "교체 요청 검토 중입니다.", State.IN_PROGRESS, 37, 15, 78.2, LocalDateTime.parse("2024-10-02T13:46:00")),
                        new Issue("user2","강의실 환경 개선을 위한 신식 에어컨 설치 건의", "시설", "저는 정경학부에 재학 중인 장상두입니다. 오늘 저는 강의실의 학습 환경 개선을 위해 건의드리고자 합니다.", "신식 에어컨 설치 요청 검토 중입니다.", State.IN_PROGRESS, 50, 20, 82.1, LocalDateTime.parse("2024-10-01T12:43:00")),
                        new Issue("user2","휴학 관련 문의", "학사", "학교 휴학 관련 문의 메일은 어디다 보낼수 있을까요?", "학사 담당 이메일을 확인하세요.", State.DONE, 18, 5, 50.8, LocalDateTime.parse("2024-09-27T17:40:00")),
                        new Issue("user2","졸업평가 면제조건", "학사", "졸업평가 면저 글경 19학번인데 졸업평가 면제조건이 어떻게되나요", "졸업평가 면제조건은 학사 공지를 참고하세요.", State.NOT_STARTED, 25, 8, 65.4, LocalDateTime.parse("2024-09-26T15:08:00")),
                        new Issue("user2","취업계 관련 유고결석 문의 건", "학사", "취업 유고결석 안녕하세요, 경제세무학과 4학년 201982072 서보혁입니다. 다름이 아니오라, 8학기 초과등록자인데 유고결석(취업계) 가능할까요?", "유고결석 처리 관련 담당자에게 문의하세요.", State.IN_PROGRESS, 40, 12, 89.6, LocalDateTime.parse("2024-09-11T14:20:00")),
                        new Issue("user2","졸업이수학점 관련 질문입니다", "학사", "학점 졸업 학점 졸업이수학점 관련 질문입니다. https://web.kangnam.ac.kr/menu/c5dc4b1d7b4dd402e5e6a7a8471eb55c.do?encMenuSeq=eb3b60b9e551de71300d75bf7894c98d", "입학년도 기준으로 학점이 계산됩니다.", State.DONE, 28, 6, 70.2, LocalDateTime.parse("2024-09-09T18:54:00")),
                        new Issue("user2", "예비졸업심사 관련", "학사", "졸업심사 학점 19년도 입학생이라 심화전공 선택해서 전공기초6학점+전공선택60학점채워야되는데...", "예비졸업심사 기준 확인 중입니다.", State.IN_PROGRESS, 35, 10, 75.9, LocalDateTime.parse("2024-09-09T15:11:00")),
                        new Issue("user3","I one pass 인증", "전산", "인증 기관코드 발급코드 휴대폰에 i-one pass 어플을 설치했으나 바로 기관코드와 아이디, 발급코드를 입력하라고 하는데...", "기관코드 발급 절차를 확인 중입니다.", State.REJECTED, 9, 2, 40.3, LocalDateTime.parse("2024-09-09T07:56:00")),
                        new Issue("user3","강의실 환경 개선을 위한 신식 에어컨 설치 건의", "시설", "에어컨 샬롬관 안녕하세요, 저는 정경학부에 재학 중인 장상두입니다. 오늘 저는 강의실의 학습 환경 개선을 위해 건의드리고자 합니다. 현재 저희 대학교 강의실의 에어컨이 오래되어 제 기능을 다하지 못하고 있습니다. 특히, 강의실에 많은 학생들이 몰리다 보니 더위가 심하게 느껴집니다...", "신식 에어컨 설치 요청을 검토 중입니다.", State.IN_PROGRESS, 30, 10, 85.3, LocalDateTime.parse("2024-10-01T12:43:00")),
                        new Issue("user3","경천관 1층 정수기 교체 관련 문의", "시설", "정수기 교체 관련 문의 경천관 1층 정수기 언제나 노고 많으십니다. 다름이 아니라 건의드리고 싶은 사항이 있어 글을 남깁니다...", "정수기 교체 가능 여부를 확인 중입니다.", State.IN_PROGRESS, 15, 4, 72.4, LocalDateTime.parse("2024-08-22T15:28:00")),
                        new Issue("user3","기숙사 신청", "시설", "가숙사 신청 2학기 기숙사 신청기간 언제까지 인가요?", "기숙사 신청기간은 학생 포털 공지를 참고하세요.", State.DONE, 8, 1, 34.8, LocalDateTime.parse("2024-06-24T12:58:00")),
                        new Issue("user3","샬롬 601호 뒷문이 너무 쾅쾅 닫힙니다", "시설", "샬롬관 문 샬롬관 조치 부탁드립니다...", "문 소음을 줄이는 대책을 논의 중입니다.", State.NOT_STARTED, 5, 0, 40.2, LocalDateTime.parse("2024-03-18T13:45:00")),
                        new Issue("user3","교육관 시설 보수 관련하여 문의 드립니다.", "시설", "천장형에어컨 교육관시설보수 안녕하세요. 교육관 건물 에어컨 시설 관련하여 건의드릴게 있어 연락 드렸습니다...", "에어컨 바람막이 설치 요청을 검토 중입니다.", State.IN_PROGRESS, 22, 8, 88.9, LocalDateTime.parse("2023-09-20T20:21:00")),
                        new Issue("user3","주차 정기권 기간 중 차량 변경", "시설", "정기권 차량변경 주차권 학기 정기권이 끊어져있는 상태인데 도중에 차량을 변경하게 되었습니다...", "차량 변경 절차를 학생 포털에서 확인하세요.", State.DONE, 10, 2, 55.7, LocalDateTime.parse("2023-05-25T18:42:00")),
                        new Issue("user3","도서관 소등, 냉난방 관련 문의드립니다.", "시설", "도서관 소등, 냉난방 도서관 오후시간에 부분소등을 하는 이유는 무엇인가요?...", "소등 및 냉난방 운영 정책을 검토 중입니다.", State.IN_PROGRESS, 12, 3, 65.1, LocalDateTime.parse("2023-04-26T18:50:00")),
                        new Issue("user3","샤워실 위치", "시설", "현재 샤워실 사용이 가능 한 위치가 어디인가요 샤워실 위치 학교에서 샤워할 일이 생길 것 같은데...", "기숙사를 제외한 샤워실 위치를 시설팀에 문의하세요.", State.DONE, 7, 1, 28.6, LocalDateTime.parse("2023-04-25T20:10:00")),
                        new Issue("user3","도서관 너무 추워요. 도서관 난방 부탁드립니다.", "시설", "도서관 난방 공부할 때 시설이 너무 추워서 난방 부탁드립니다.", "난방 운영 시간을 조정 중입니다.", State.REJECTED, 4, 0, 19.2, LocalDateTime.parse("2023-04-06T17:44:00")),
                        new Issue("user3","기숙사 헬스장 운영 관련하여 문의합니다.", "시설", "생활건강헬스센터 학교 시설 운영 문의 기숙사 비에 헬스장 운영비용도 포함되어 있는 것으로 알고 있는데...", "헬스장 운영비와 개선 계획을 확인 중입니다.", State.IN_PROGRESS, 20, 7, 91.7, LocalDateTime.parse("2023-04-05T22:53:00"))

                ));
        return issueList;
    }
}
