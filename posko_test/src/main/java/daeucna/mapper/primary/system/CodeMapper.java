package daeucna.mapper.primary.system;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import daeucna.system.code.CodeDto;

@Mapper
public interface CodeMapper {
   List<CodeDto> getCmmnCode(CodeDto param);
   int saveCmmnCode(CodeDto param);   
   int saveCmmnCodeNls(CodeDto param);
   void deleteCmmnCode(CodeDto param);   
   void deleteCmmnCodeNls(CodeDto param);   
}