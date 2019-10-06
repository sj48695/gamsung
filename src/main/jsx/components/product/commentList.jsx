import React, { Component, Fragment } from 'react';
import * as ProductService from '../../services/ProductService.js';

import dateFormat from 'dateformat';

class CommentList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            comments: []
        }
        this.onWriteComment = this.onWriteComment.bind(this);
        this.onEditComment = this.onEditComment.bind(this);
        this.onDeleteComment = this.onDeleteComment.bind(this);
        this.onBack = this.onBack.bind(this);
    }


    getCommentList() {
        const promise = ProductService.getCommentList(27);
        promise.then((comments) => {
            this.setState({ comments: comments });
        }).catch((err) => {
            console.log(err);
            return;
        });

    }
    onWriteComment() {
        var formData = $('#commentform').serialize();

        const promise = ProductService.writeComment(formData);
        promise.then((comments) => {
            // this.setState({ comments: comments });
            alert(data);

            this.getCommentList();
        }).catch((err) => {
            console.log(err);
            return;
        });
    }
    onWriteReComment() {
        var content = $('#recomment-form textarea').val();
        if (content.length == 0) return;

        var recommentData = $('#recomment-form').serialize();

        const promise = ProductService.writeReComment(recommentData);
        promise.then((comments) => {
            // this.setState({ comments: comments });
            alert(data);
            alert('success');
            $('#write-recomment-modal').modal('hide'); //hide bootstrap modal
            // $('#recomment-form').each(() {
            //     this.reset();
            // });
            this.getCommentList();
        }).catch((err) => {
            console.log(err);
            return;
        });
    }

    onEditComment() {

        this.getCommentList();
    }
    onUpdateComment() {
        var commentNo = $(this).attr('data-commentno');
        var content = $('#updateform' + commentNo + ' textarea').val();
        var inputData = $('#updateform' + commentNo).serialize();


        const promise = ProductService.updateComment(inputData);
        promise.then(() => {
            alert('댓글을 수정했습니다.');
            var span = $('#commentview' + commentNo + ' span');
            span.html(content.replace(/\n/gi, '<br>'));
            //view-div는 숨기고, edit-div는 표시하기	
            $('#commentview' + commentNo).css('display', 'block');
            $('#commentedit' + commentNo).css('display', 'none');
            this.getCommentList();
        }).catch((err) => {
            console.log(err);
            alert('댓글 수정 실패');
            return;
        });
    }
    onDeleteComment() {
        commentNo = $(this).attr('data-commentno');

        const promise = ProductService.deleteComment(commentNo);
        promise.then(() => {
            if (data == 'success') {
                $('#tr' + commentNo).remove();
                alert('삭제했습니다.');
            } else {
                alert('삭제 실패 1');
            }
            this.getCommentList();
        }).catch((err) => {
            console.log(err);
            alert('삭제 실패 2');
            return;
        });
    }

    onBack() {

    }

    componentDidMount() {
        this.getCommentList();
    }

    render() {
        const { comments } = this.state;

        var commentRows;
        if (comments.length > 0) {
            commentRows = comments.map((comment) => {

                return (
                    <Fragment>
                        {/* <!-- comment list --> */}
                        <tr id="tr${ comment.commentNo }">
                            <td style={{ textAlign: 'left', margin: '5px', borderBottom: "solid 1px", paddingLeft: `${comment.depth * 20}px` }} >
                                <div id='commentview${ comment.commentNo }'>
                                    ${comment.writer}  [${comment.regDate}]
                                    <span> ${comment.content} </span>
                                    <div test='${ loginuser.memberId eq "manager" }'>
                                        <a className="editcomment " data-commentno='${ comment.commentNo }' onClick={this.onEditComment}>edit</a>
                                        <a className="deletecomment " onClick={this.onDeleteComment} data-commentno="${ comment.commentNo }">delete</a>
                                    </div>
                                    <div test='${ loginuser.memberId ne "manager" }'>
                                        <div style={{ display: `${loginuser.memberId == comment.writer ? "block" : "none"}` }}>
                                            <a className="editcomment " data-commentno='${ comment.commentNo }' onClick={this.onEditComment}>edit</a>
                                            <a className="deletecomment " onClick={this.onDeleteComment} data-commentno="${ comment.commentNo }">delete</a>
                                        </div>
                                    </div>
                                    <a className="recomment-link button button-subscribe btn-xs" data-commentno="${ comment.commentNo }">WriteComment</a>
                                </div>
                                {/* <!-- 수정 --> */}
                                <div id='commentedit${ comment.commentNo }' style={{ display: "none" }}>
                                    ${comment.writer}  [${comment.regDate}]
                                    <form id="updateform${ comment.commentNo }">
                                        <input type="hidden" name="commentNo" value="${ comment.commentNo }" />
                                        <textarea name="content" style={{ width: "550px" }} rows="3" maxlength="200">${comment.content}</textarea>
                                    </form>
                                    <div>
                                        <a className="updatecomment " onClick={this.onUpdateComment} data-commentno="${ comment.commentNo }">edit</a>
                                        <a className="cancel " data-commentno="${ comment.commentNo }" onClick={this.onBack}>back</a>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </Fragment>
                );
            });
        } else {
            commentRows = <div>댓글이 없습니다.</div>
        }
        return (
            <Fragment>
                {/* <!-- write comment area --> */}
                <form id="commentform">
                    <input type="hidden" name="questionNo" value="${ question.questionNo }" />
                    <input type="hidden" name="writer" value="${ loginuser.memberId }" />
                    <table style={{ width: '550px', border: 'solid 1px', margin: '0 auto' }} className="table table-bordered">
                        <tr>
                            <td style={{ width: "500px" }}>
                                <textarea id="comment_content" name="content" style={{ width: "500px" }} rows="3" className="form-control"></textarea>
                            </td>
                            <td style={{ width: "50px", verticalAlign: "middle" }}>
                                <a id="writecomment" onClick={this.onWriteComment} style={{ textDecoration: "none" }}>댓글등록</a>
                            </td>
                        </tr>
                    </table>
                </form>
                <div>
                    <hr style={{ width: "550px", margin: "0 auto" }} />
                    <table id="comment-list" style={{ width: "550px", border: "solid 1px", margin: "0 auto" }} className="table table-bordered">
                        {commentRows}
                    </table>
                </div>
            </Fragment>
        );
    }
}

export default CommentList;