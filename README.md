# KanReAttribute - Spigot服务器RPG插件

<h5 align="center">
<a href="#introduction">插件简介</a>
<a href="#features">插件特点</a>
<a href="#config">基本信息及配置</a>
<a href="#tags">标签</a>
<a href="#tips">提示</a>
<a href="#warning">警告</a>
</h5>
<h4 align="center">v 1.1.0</h4>

<a name="introduction"></a>
## 插件简介

* KanReAttribute是一个RPG插件,提供了较为基础的属性
* 目前此插件还在测试中,内部api随时会更改,仅供娱乐,bug数量未知
* 欢迎发现bug并提交issue,也欢迎提出意见和建议

<a name="features"></a>
## 插件特点
* RPG物品类型与RPG物品分离，RPG物品可以在配置文件中修改，不会把lore当成属性


<a name="config"></a>
## 基本信息及配置

<a name="tags"></a>
## 插件内置的属性

### 插件内置的伤害属性
如果不是特别标注,则默认支持负数(没有测试过±Infinity,NaN等极端值)

#### 伤害属性
* Damage 基础伤害
* Strength 力量,除以100(可以在配置文件中更改)后与伤害相乘(加1后)
* CritDamage 暴击增幅,除以100(可以在配置文件中更改)后与伤害相乘(加1后)
* CritChance 暴击几率
  <br>
#### 防御属性(TODO)
* Defense 防御,计算公式如下:
  $$
  damage - damage \times \frac{defense}{100}
  $$

<a name="tips"></a>
## 提示
* 该插件使用了千问AI大模型进行辅助开发，但是核心架构是我自己设计的，不必过度担心，代码我已经审查过了

<a name="warning"></a>
## 警告
* 该插件目前还在测试中(WIP),内部api随时会更改,仅供娱乐,bug数量未知,请勿在生产环境中使用
* 该插件很多功能都没有完成，比如命令的权限校验，一定不要放在生产环境中使用