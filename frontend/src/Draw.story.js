import React from 'react'
import { storiesOf } from '@storybook/react'
import Draw from './Draw.jsx'

storiesOf('Draw/Colors and values', module)
  .add('Purple five', () => <Draw faces={[1, 2, 3, 4, 5]} color='PURPLE' value={1} />)
  .add('Blue five', () => <Draw faces={[2, 3, 4, 5, 6]} color='BLUE' value={1} />)
  .add('Yellow four', () => <Draw faces={[1, 2, 3, 4]} color='YELLOW' value={1} />)
  .add('Green three', () => <Draw faces={[1, 2, 3]} color='GREEN' value={1} />)
  .add('Red two', () => <Draw faces={[1, 2]} color='RED' value={1} />)
  .add('Orange one', () => <Draw faces={[1]} color='ORANGE' value={1} />)

storiesOf('Draw/Requested value/Regular', module)
  .add('Six', () => <Draw faces={[6, 1, 2, 4, 6]} color='RED' requestedValue={6} />)
  .add('Five', () => <Draw faces={[6, 1, 2, 4, 6]} color='RED' requestedValue={5} />)
  .add('Four', () => <Draw faces={[6, 1, 2, 4, 6]} color='RED' requestedValue={4} />)
  .add('Three', () => <Draw faces={[6, 1, 2, 4, 6]} color='RED' requestedValue={3} />)
  .add('Two', () => <Draw faces={[6, 1, 2, 4, 6]} color='RED' requestedValue={2} />)
  .add('Paco', () => <Draw faces={[6, 1, 2, 4, 6]} color='RED' requestedValue={1} />)

storiesOf('Draw/Requested value/Palifico', module)
  .add('Six', () => <Draw faces={[6, 1, 2, 4, 6]} color='RED' requestedValue={6} palifico />)
  .add('Five', () => <Draw faces={[6, 1, 2, 4, 6]} color='RED' requestedValue={5} palifico={true} />)
  .add('Four', () => <Draw faces={[6, 1, 2, 4, 6]} color='RED' requestedValue={4} palifico={true} />)
  .add('Three', () => <Draw faces={[6, 1, 2, 4, 6]} color='RED' requestedValue={3} palifico />)
  .add('Two', () => <Draw faces={[6, 1, 2, 4, 6]} color='RED' requestedValue={2} palifico />)
  .add('Paco', () => <Draw faces={[6, 1, 2, 4, 6]} color='RED' requestedValue={1} palifico />)
