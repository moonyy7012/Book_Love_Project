package com.ssafy.core.repository;

import com.ssafy.core.entity.*;
import com.ssafy.core.code.JoinCode;
import com.ssafy.core.code.YNCode;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class UserRepoCommonImpl implements UserRepoCommon{
    private final JPAQueryFactory queryFactory;
    private EntityManager em;

    public UserRepoCommonImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }


    @Override
    @Transactional
    public User findUserLogin(String id, JoinCode type){
        User result = queryFactory
                .select(QUser.user)
                .from(QUser.user)
                .where(QUser.user.id.eq(id), QUser.user.type.eq(type))
                .leftJoin(QUser.user.categories, QCategory.category)
                .fetchJoin()
                .fetchFirst();

        return result;
    }


    @Override
    public User findById(String id){
        User result = queryFactory
                .select(QUser.user)
                .from(QUser.user)
                .where(QUser.user.id.eq(id))
                .fetchOne();
        return result;
    }


    @Override
    public void deleteUser(User user){
//        QUserStudy userStudy = QUserStudy.userStudy;
//        QStudyMember studyMember = QStudyMember.studyMember;
//        QStudyPlan studyPlan = QStudyPlan.studyPlan;
//        QStudyNotice studyNotice = QStudyNotice.studyNotice;
//        QBoard board = QBoard.board;
//        QComment comment = QComment.comment;
//        QUserPlan userPlan = QUserPlan.userPlan;
//        QAlertMsg alertMsg = QAlertMsg.alertMsg;
//
//        long userId = user.getUserId();
//        queryFactory.delete(comment).where(comment.user.userId.eq(userId)).execute();
//        queryFactory.delete(board).where(board.user.userId.eq(userId)).execute();
//        queryFactory.delete(userStudy).where(userStudy.user.userId.eq(userId)).execute();
//        queryFactory.delete(studyMember).where(studyMember.user.userId.eq(userId)).execute();
//        queryFactory.delete(studyPlan).where(studyPlan.user.userId.eq(userId)).execute();
//        queryFactory.delete(studyNotice).where(studyNotice.user.userId.eq(userId)).execute();
//        queryFactory.delete(userPlan).where(userPlan.user.userId.eq(userId)).execute();
//        queryFactory.delete(user).where(alertMsg.user.userId.eq(userId)).execute();



    }

//
//
//    // isBind 조건만 체크
//    public BooleanExpression checkUserIsBind(YNCode isBind){
//        if(isBind == null)
//            return null;
//
//        return QUser.user.isBind.eq(isBind);
//    }
}





