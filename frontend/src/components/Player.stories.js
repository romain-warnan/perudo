import React from 'react'
import { storiesOf } from '@storybook/react'
import Player from './Player'

storiesOf('Player', module)
  .add('Purple player', () => <Player id='21222f0b-44d7-4264-bbef-3516ca2f7b22' name='Romain' color='PURPLE' />)
  .add('Unregistered', () => <Player />)
