import Vue from 'vue'

import * as echarts from 'echarts'
//新增
import {
    TooltipComponent,
    GridComponent,
    LegendComponent
} from 'echarts/components';
import { LineChart } from 'echarts/charts';
import { UniversalTransition } from 'echarts/features';
import { CanvasRenderer } from 'echarts/renderers';

echarts.use([
    TooltipComponent,
    GridComponent,
    LegendComponent,
    LineChart,
    CanvasRenderer,
    UniversalTransition
]);

Vue.prototype.$echarts = echarts
