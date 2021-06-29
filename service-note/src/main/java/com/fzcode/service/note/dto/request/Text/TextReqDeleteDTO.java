package com.fzcode.service.note.dto.request.Text;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
@Data
public class TextReqDeleteDTO {
    @NotEmpty
    private Integer tid;

}
