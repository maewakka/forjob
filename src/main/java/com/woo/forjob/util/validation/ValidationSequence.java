package com.woo.forjob.util.validation;

import javax.validation.GroupSequence;

@GroupSequence({ValidationGroup.NotBlankGroup.class, ValidationGroup.EmailCheckGroup.class, ValidationGroup.SizeCheckGroup.class,
        ValidationGroup.PatternCheckGroup.class})
public interface ValidationSequence {
}
