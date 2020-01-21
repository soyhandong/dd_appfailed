package kr.co.ddapp.api.v1.news;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class NewsDao {
    @Resource(name = "mainSqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    public List<Map<String, Object>> findAptNameList(Map<String, Object> params) {
        return sqlSessionTemplate.selectList("v1_asset_apt.findAptNameList", params);
    }
}
