package studentorder.validator;

import studentorder.domain.children.AnswerChildren;
import studentorder.domain.StudentOrder;

public class ChildrenValidator {
    public AnswerChildren checkChildren(StudentOrder so) {
        System.out.println("Children check is running");
        return new AnswerChildren();
    }
}
