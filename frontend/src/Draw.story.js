import React from 'react'

import { storiesOf } from '@storybook/react'
// import { action } from '@storybook/addon-actions'
// import { linkTo } from '@storybook/addon-links'

import Draw from './Draw.jsx'

storiesOf('Draw/Colors and values', module)
  .add('Purple five', () => <Draw faces={[1, 2, 3, 4, 5]} color='PURPLE' value='1' />)
  .add('Red five', () => <Draw faces={[2, 3, 4, 5, 6]} color='BLUE' value='1' />)
  .add('Yellow four', () => <Draw faces={[1, 2, 3, 4]} color='YELLOW' value='1' />)
  .add('Green three', () => <Draw faces={[1, 2, 3]} color='GREEN' value='1' />)
  .add('Red two', () => <Draw faces={[1, 2]} color='RED' value='1' />)
  .add('Orange one', () => <Draw faces={[1]} color='ORANGE' value='1' />)

storiesOf('Draw/Requested value', module)
  .add('Six', () => <Draw faces={[6, 1, 2, 4, 6]} color='RED' requestedValue={6} />)
  .add('Five', () => <Draw faces={[6, 1, 2, 4, 6]} color='RED' requestedValue={5} />)
  .add('Four', () => <Draw faces={[6, 1, 2, 4, 6]} color='RED' requestedValue={4} />)
  .add('Three', () => <Draw faces={[6, 1, 2, 4, 6]} color='RED' requestedValue={3} />)
  .add('Two', () => <Draw faces={[6, 1, 2, 4, 6]} color='RED' requestedValue={2} />)
  .add('Paco', () => <Draw faces={[6, 1, 2, 4, 6]} color='RED' requestedValue={1} />)
