package online.shixun.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import online.shixun.pojo.vo.GoodsVo;
import online.shixun.service.GoodsService;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	GoodsService goodsService;
	
	/**
	 * 获得秒杀商品列表
	 * */
    @RequestMapping(value="/to_list")
    public String list(Model model) {
    	List<GoodsVo> goodsList = goodsService.listGoodsVo();
    	model.addAttribute("goodsList", goodsList);
    	 return "goods_list";
    }

	/**
	 *
	 * @param model
	 * @param goodsId
	 * @return
	 */
	@RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model,@PathVariable("goodsId")long goodsId) {
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	model.addAttribute("goods", goods);
    	
    	long startAt = goods.getStartDate().getTime();
    	long endAt = goods.getEndDate().getTime();
    	long now = System.currentTimeMillis();
    	
    	int seckillStatus = 0;
    	int remainSeconds = 0;
    	if(now < startAt ) {//秒杀还没开始，倒计时
    		seckillStatus = 0;
    		remainSeconds = (int)((startAt - now )/1000);
    	}else  if(now > endAt){//秒杀已经结束
    		seckillStatus = 2;
    		remainSeconds = -1;
    	}else {//秒杀进行中
    		seckillStatus = 1;
    		remainSeconds = 0;
    	}
    	model.addAttribute("seckillStatus", seckillStatus);
    	model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }
    
}
