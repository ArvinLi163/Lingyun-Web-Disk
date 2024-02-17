<template>
  <div>
    <!-- 数据统计区域 -->
    <div>
      <el-row :gutter="10">
        <el-col :span="6">
          <!-- 卡片1 -->
          <el-card>
            <div class="card_title">
              <span><i class="el-icon-user-solid">网盘总用户：{{ userCount }}个 </i></span>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <!-- 卡片2 -->
          <el-card>
            <div class="card_title">
              <span><i class="el-icon-coin">网盘总数：{{ fileCount.total }} 个 </i></span>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <!-- 卡片3 -->
          <el-card>
            <div class="card_title">
              <span>
                <i class="el-icon-folder-opened">文件夹总数：{{ fileCount.dircounts }} 个</i>
              </span>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <!-- 卡片3 -->
          <el-card>
            <div class="card_title">
              <i class="el-icon-document"> 文件总数：{{ fileCount.filecounts }} 个</i>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <el-row class="row_counts" :gutter="10">
      <el-col :span="24">
        <!-- 卡片1 -->
        <el-card class="echart_card">
          <div class="charts_line" ref="charts"></div>
          <div class="charts_pie" ref="charts1"><span>文件类型占比</span></div>
        </el-card>
      </el-col>
    </el-row>

  </div>
</template>

<script>
export default {
  data() {
    return {
      userCount: "",
      fileCount: { total: "", dircounts: "", filecounts: "" },
      xdata: [],
      ydata: [],
    };
  },
  created() {
    this.getUserCount();
    this.getFileCount();
    this.getFileCountByDay();

  },
  methods: {
    async getUserCount() {
      const { data: res } = await this.$http.get("getUserCount");
      console.log(res);
      this.userCount = res.data.usercount;
    },
    async getFileCount() {
      const { data: res } = await this.$http.get("getFileCount");
      this.fileCount.total = res.data.total;
      this.fileCount.dircounts = res.data.dircounts;
      this.fileCount.filecounts = res.data.filecounts;
      let myChart1 = this.$echarts.init(this.$refs.charts1);

      myChart1.setOption({
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '5%',
          left: 'center',
          // doesn't perfectly work with our tricks, disable it
          selectedMode: false
        },
        series: [
          {
            //name: 'Access From',
            type: 'pie',
            radius: ['45%', '70%'],
            center: ['50%', '65%'],
            // adjust the start angle
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            startAngle: 180,
            label: {
              show: true,
              formatter(param) {
                // correct the percentage
                return param.name + ' (' + param.percent * 2 + '%)';
              }
            },

            emphasis: {
              label: {
                show: true,
                fontSize: 10,
                fontWeight: 'bold'
              }
            },
            data: [
              { value: 14, name: '文件夹总数' },
              { value: 36, name: '文件总数' },
              {
                // make an record to fill the bottom 50%
                value: 14 + 36,
                itemStyle: {
                  // stop the chart from rendering this piece
                  color: 'none',
                  decal: {
                    symbol: 'none'
                  }
                },
                label: {
                  show: false
                }
              }
            ]
          }
        ]
      });

    },
    async getFileCountByDay() {
      const { data: res } = await this.$http.get("getFileCountByDay");
      let myChart = this.$echarts.init(this.$refs.charts);
      this.xdata = res.data.name;
      this.ydata = res.data.value;
      var option;
      let xLabel = [
        this.xdata[0],
        this.xdata[1],
        this.xdata[2],
        this.xdata[3],
        this.xdata[4],
        this.xdata[5],
        this.xdata[6]
      ];
      //console.log(xLabel);
      let goToSchool = [this.ydata[0].toString(), this.ydata[1].toString(), this.ydata[2].toString(), this.ydata[3].toString(), this.ydata[4].toString(), this.ydata[5].toString(), this.ydata[6].toString()];
      //console.log(goToSchool);
      option = {
        backgroundColor: '#fff',
        tooltip: {
          trigger: 'axis',
          backgroundColor: 'transparent',
          axisPointer: {
            lineStyle: {
              color: {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [{
                  offset: 0,
                  color: 'rgba(126,199,255,0)' // 0% 处的颜色
                }, {
                  offset: 0.5,
                  color: 'rgba(126,199,255,1)' // 100% 处的颜色
                }, {
                  offset: 1,
                  color: 'rgba(126,199,255,0)' // 100% 处的颜色
                }],
                global: false // 缺省为 false
              }
            },
          },

        },
        legend: {
          align: "left",
          right: '10%',
          top: '3%',
          type: 'plain',
          textStyle: {
            color: '#000',
            fontSize: 16
          },
          // icon:'rect',
          itemGap: 25,
          itemWidth: 18,
          icon: 'path://M0 2a2 2 0 0 1 2 -2h14a2 2 0 0 1 2 2v0a2 2 0 0 1 -2 2h-14a2 2 0 0 1 -2 -2z',
          data: ['新增文件数']
        },
        grid: {
          top: '15%',
          left: '10%',
          right: '10%',
          bottom: '10%',
          //containLabel: true
        },
        xAxis: [{
          type: 'category',
          boundaryGap: false,
          axisLine: { //坐标轴轴线相关设置。数学上的x轴
            show: true,
            lineStyle: {
              color: '#000'
            },
          },
          axisLabel: { //坐标轴刻度标签的相关设置
            textStyle: {
              color: '#000',
              padding: 16,
              fontSize: 14
            },
            formatter: function (data) {
              return data
            }
          },
          splitLine: {
            show: false,
            lineStyle: {
              color: '#000'
            },
          },
          axisTick: {
            show: true,
            inside: true
          },
          data: xLabel
        }],
        yAxis: [{
          // name: '新增文件数',
          nameTextStyle: {
            color: "#7ec7ff",
            fontSize: 16,
            padding: 10
          },
          min: 0,
          splitLine: {
            show: true,
            lineStyle: {
              type: 'dashed',
              color: '#192a44'
            },
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: "#000"
            },

          },
          axisLabel: {
            show: true,
            textStyle: {
              color: '#000',
              padding: 16
            },
            formatter: function (value) {
              if (value === 0) {
                return value
              }
              return value
            }
          },
          // 坐标轴刻度相关设置
          axisTick: {
            show: true,//显示刻度
          },
        },
        ],
        series: [{
          name: '新增文件数',
          type: 'line',
          symbol: 'circle', // 默认是空心圆（中间是白色的），改成实心圆
          showAllSymbol: true,
          symbolSize: 0,
          smooth: true,
          lineStyle: {
            normal: {
              width: 5,
              color: "rgba(25,163,223,1)", // 线条颜色
            },
            borderColor: 'rgba(0,0,0,.4)',
          },
          itemStyle: {
            color: "rgba(25,163,223,1)",
            borderColor: "#646ace",
            borderWidth: 2
          },
          tooltip: {
            show: true
          },
          data: goToSchool
        },
        ]
      };
      let len = 0
      setInterval(() => {
        if (len === xLabel.length) {
          len = 0
        }
        myChart.dispatchAction({
          type: 'showTip',
          seriesIndex: 0,
          dataIndex: len,
        })
        len++
      }, 1000);
      myChart.setOption(option);
    },

  },
  mounted() {
    //初始化echarts实例
  },
};
</script>

<style lang="less" scoped>
//1623 710
.charts_line {
  width: 800px;
  height: 540px;
  float: left;
}

.charts_pie {
  width: 375px;
  height: 375px;
  float: right;
  margin-top: 12%;
  margin-right: 2%;
  margin-bottom: 1%;
}
</style>

