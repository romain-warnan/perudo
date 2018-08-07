import React from 'react'
import { storiesOf } from '@storybook/react'
import Face from './Face'

storiesOf('Face/Colors', module)
  .add('Red', () => <Face color='RED' value='1' />)
  .add('Blue', () => <Face color='BLUE' value='1' />)
  .add('Yellow', () => <Face color='YELLOW' value='1' />)
  .add('Orange', () => <Face color='ORANGE' value='1' />)
  .add('Purple', () => <Face color='PURPLE' value='1' />)
  .add('Green', () => <Face color='GREEN' value='1' />)

storiesOf('Face/Values', module)
  .add('Paco', () => <Face color='BLUE' value='1' />)
  .add('Two', () => <Face color='BLUE' value='2' />)
  .add('Three', () => <Face color='BLUE' value='3' />)
  .add('Four', () => <Face color='BLUE' value='4' />)
  .add('Five', () => <Face color='BLUE' value='5' />)
  .add('Six', () => <Face color='BLUE' value='6' />)

storiesOf('Face/Shaded', module)
  .add('Paco', () => <Face color='BLUE' value='1' shaded />)
  .add('Two', () => <Face color='BLUE' value='2' shaded='true' />)
  .add('Three', () => <Face color='BLUE' value='3' shaded='1' />)
  .add('Four', () => <Face color='BLUE' value='4' shaded />)
  .add('Five', () => <Face color='BLUE' value='5' shaded />)
  .add('Six', () => <Face color='BLUE' value='6' shaded />)
