package com.kachiingapp.kachiing.data.dao.oracle;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.kachiingapp.kachiing.domain.entity.gongyingshang.TransDetailView;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: OracleMapper
 * @Auther: liujunhan
 * @Date: 2024/1/15 09:45
 * @Description:
 */
@Mapper
@Repository
@DS("slave")
public interface OracleMapper {

    void changeSession();

    List<TransDetailView> getCollectTransDetailsData(String timeCondition);
}
