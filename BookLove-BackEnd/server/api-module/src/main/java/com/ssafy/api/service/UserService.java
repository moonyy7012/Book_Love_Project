//package com.ssafy.api.service;
//
//import com.ssafy.core.entity.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class UserService {
//
//    @Transactional
//    public UserStudy findUserApply(User user, StudyClass studyClass) throws Exception{
//        UserStudy userStudy = userStudyRepository.findUserApply(user, studyClass);
//        return userStudy;
//    }
//    @Transactional
//    public List<UserStudy> getapplylist(int sort, StudyClass studyClass){
//        return userStudyRepository.getapplylist(sort, studyClass);
//    }
//
//    @Transactional(readOnly = false)
//    public void addStudyMember(StudyMember studyMember){
//        studyMemberRepository.save(studyMember);
//    }
//
//    @Transactional(readOnly = false)
//    public void delUserStudy(UserStudy userStudy){
//        userStudyRepository.delete(userStudy);
//    }
//
//
//    public List<StudyMember> getStudyMemberListByUserId(long userid){
//        return studyMemberRepository.getStudyMemberListByUserId(userid);
//    }
//
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
